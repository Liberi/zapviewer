plugins {
    id(Plugins.android_library)
    id(Plugins.kotlin_android)
}

android {
    compileSdkVersion(Versions.compileSdk)
    buildToolsVersion(Versions.buildTools)

    defaultConfig {
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)
        versionCode = Config.version_code
        versionName = Config.version_name

        buildConfigField("String", "BASE_URL", BuildConfig.base_url)
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(project(Modules.common))

    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))
    implementation (Deps.kotlin_stdlib)
    implementation (Deps.androidx_ktx)

    implementation (Deps.retrofit_core)
    implementation (Deps.retrofit_converter)
    implementation (Deps.retrofit_rxjava)
}