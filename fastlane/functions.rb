require 'fastlane_core'

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
    icon: $project[:icon],
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

# ----------------- Send message on Slack ----------------- #
def notify(options)
  fields = [{
    title: "Application",
    value: options[:name],
    short: true
  }]

  unless options[:version].nil? then
    fields << {
      title: "Version",
      value: options[:version],
      short: true
    }
  end

  unless options[:platform].nil? then
    if options[:platform] == "android" then
      icon = "https://avatars.slack-edge.com/2020-01-02/879774548883_130b9d6df6ea40b3509a_72.png"
    else
      icon = "https://avatars.slack-edge.com/temp/2020-02-12/935992540242_4cdcac5375f580cee561.png"
    end
  end

  slack(
    slack_url: ENV["SLACK_URL"],
    username: options[:username].nil? ? "Release Train 🚂" : options[:username],
    message: options[:message],
    success: options[:success].nil? ? true : options[:success],
    icon_url: options[:icon],
    default_payloads: [],
    payload: {
      "Date and Time" => Time.now.strftime("%d/%m/%Y %H:%M")
    },
    attachment_properties: {
      thumb_url: options[:icon],
      image_url: options[:image],
      fields: fields,
      footer: options[:platform].upcase,
      footer_icon: icon
    })
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


# ----------------- Close issues ----------------- #
def close_issues(repo, changelog)

  issues = get_issues(repo, changelog)

  # Close issues
  issues.each do |issue|
    github_close_issue(repo: "olxbr/squad-mobile-apps", issue: issue[:number], author: "ci-mobile-grupozap")
  end

  return issues
end

# -------------- Get Public Changelog ------------- #
def get_whatsnew(issues)
  whatsnew = []
  issues.each do |issue|
    news = github_whats_new(repo: "olxbr/squad-mobile-apps", issue: issue[:number])
    if !whatsnew.include? news
      whatsnew.push(news)
    end
  end

  return whatsnew
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