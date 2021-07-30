require 'fastlane_core'

#------------------------------- Default project config -------------------------------#
$project = {
  :path => "#{Dir.pwd}/..",
  :platform => "android",
  :name => "zapViewer",
  :version => "0.0.1"
}

#------------------------------ Get properties from file ------------------------------#
def version(file)
  properties = {}
  IO.foreach(file) do |line|
    properties[$1.strip] = $2 if line =~ /([^=]*)=(.*)\/\/(.*)/ || line =~ /([^=]*)=(.*)/
  end

  return properties
end

#------------------------------ Update properties on file -----------------------------#
def updateVersion(file, version)
  properties = {}
  IO.foreach(file) do |line|
    properties[$1.strip] = $2 if line =~ /([^=]*)=(.*)\/\/(.*)/ || line =~ /([^=]*)=(.*)/
  end

  properties["version"] = version
  properties["code"] = properties["code"].to_i + 1

  file = File.new(file, "w+")
  properties.each do |key,value|
    file.puts "#{key}=#{value}\n"
  end
  file.close

  return properties
end
#-------------------------------- Get default options -------------------------------#
def mergeDefaults(options)
  return $project = $project.merge!(options)
end

#---------------------------------- Notify success ----------------------------------#
def success(message, image = "")
  notify(
    message: message,
    platform: $project[:platform],
    name: $project[:name],
    image: image,
    version: $project[:version])

  FastlaneCore::UI.success message
end

#----------------------------------- Notify error -----------------------------------#
def failure(message, image = "")
  notify(
    success: false,
    message: "💥  #{message}",
    platform: $project[:platform],
    name: $project[:name],
    icon: $project[:icon],
    image: image,
    version: $project[:version])

  FastlaneCore::UI.error "💥  #{message}"
  raise RuntimeError, message
end

# ----------------- Create badge ----------------- #
def badge(options)
  svg = <<-EOF
  <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="112" height="20">
  <linearGradient id="b" x2="0" y2="100%">
  <stop offset="0" stop-color="#bbb" stop-opacity=".1"/>
  <stop offset="1" stop-opacity=".1"/>
  </linearGradient>
  <clipPath id="a">
  <rect width="112" height="20" rx="3" fill="#fff"/>
  </clipPath>
  <g clip-path="url(#a)">
  <path fill="#555" d="M0 0h73v20H0z"/>
  <path fill="#97ca00" d="M73 0h39v20H73z"/>
  <path fill="url(#b)" d="M0 0h112v20H0z"/>
  </g>
  <g fill="#fff" text-anchor="middle" font-family="DejaVu Sans,Verdana,Geneva,sans-serif" font-size="110">
  <text x="375" y="150" fill="#010101" fill-opacity=".3" transform="scale(.1)" textLength="630">last version</text>
  <text x="375" y="140" transform="scale(.1)" textLength="630">last version</text>
  <text x="915" y="150" fill="#010101" fill-opacity=".3" transform="scale(.1)" textLength="290">#{options[:version]}</text>
  <text x="915" y="140" transform="scale(.1)" textLength="290">#{options[:version]}</text>
  </g>
  </svg>
  EOF

  unless options[:file].nil? then
    File.write(options[:file], svg)
  end
end

# ----------------- Create log file ----------------- #
def log(options)
  unless options[:file].nil? && options[:message].nil? then
    directory = File.dirname(options[:file])
    unless File.directory?(directory)
      FileUtils.mkdir_p(directory)
    end
    File.write(options[:file], options[:message])
  end
end

# ----------------- Get Last Changelog ----------------- #
def last_changelog(path)
  changelog = File.read(path) rescue "No changelog provided"
  return getTextBefore("\n# ", changelog)
end

# ----------------- Open Pull Request ----------------- #
def open_pull_request(repo, changelog, version, files = "*")
  # get the name from current branch
  base_branch_name = Helper.backticks("git rev-parse --abbrev-ref HEAD").strip!
  head_branch_name = "chore/bump"

  # Create new branch called
  sh("git checkout -b #{head_branch_name}")

  # Add changed files
  git_add

  # Commit them
  git_commit(
    path: files,
    message: "Update files to version: #{version}" )

  # Push new branch
  push_to_git_remote(
    remote: "origin",
    local_branch: head_branch_name,
    tags: false
  )

  # Create Pull Request
  create_pull_request(
    repo: repo,
    title: "chore: Bump up version to: #{version}",
    body: changelog,
    head: head_branch_name,
    base: base_branch_name,
    labels: ["approved"]
  )
end

# ----------------- Get issues ----------------- #
def get_issues(repo, changelog)

  issues = []

  # Get Pull Request Issues
  changelog.scan(/#(\d[\d]+)/i) do |number|
    issues += github_pr_issues(repo: repo, pr: number.first)
  end

  return issues
end

# ----------------- Create Release ----------------- #
def create_release(repo, version, changelog)

  # Create GitHub Release
  return set_github_release(
    repository_name: repo,
    api_token: ENV["GITHUB_API_TOKEN"],
    name: version,
    tag_name: version,
    description: changelog)
end

# ------------ Validate Required Params ------------ #
def validateRequiredParams(options, required)
  required.each do |param|
    FastlaneCore::UI.user_error!("You should specify the \":#{param}\" parameter") unless (options[param] and not options[param].empty?)
  end
end

# ---------------- Fix commit URL ------------------ #
def fixedCommitURL(repo, changelog)
  return changelog.gsub(/\[(.+)\]\(([A-Za-z0-9\:\/\. ]+)(\"(.+)\")?\)/, '[\1](https://github.com/' + repo + '/commit\2)')
end
def getTextBefore(target, text)
  text.split(target)[0].strip
end