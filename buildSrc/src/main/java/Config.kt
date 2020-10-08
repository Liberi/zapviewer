object Config {
    const val version_code = 1
    const val version_name = "1.0"

    const val app_id = "cess.com.br.zapviewer"

    const val test_runner = "cess.com.br.zapviewer.MockTestRunner"
}

object BuildConfig {
    const val base_url = "\"http://grupozap-code-challenge.s3-website-us-east-1.amazonaws.com/sources/source-1.json/\""
}

object BuildType {
    const val debug = "debug"
    const val release = "release"
}

object Modules {
    const val app = ":app"
    const val design = ":design"
    const val common = ":common"
    const val network = ":network"
    const val dataBase = ":database"
    const val component = ":component"
    const val productList = ":productList"
    const val productDetail = ":productDetail"
    const val productChooser = ":productchooser"
}