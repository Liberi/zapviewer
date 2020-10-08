object Versions {
    const val minSdk = 24
    const val targetSdk = 29
    const val compileSdk = 29

    const val junit = "4.12"
    const val koin = "2.1.6"
    const val arch = "1.1.1"
    const val gson = "2.8.6"
    const val coil = "0.12.0"
    const val testx = "1.2.0"
    const val gradle = "4.0.1"
    const val kotlin = "1.4.0"
    const val rxjava = "2.3.0"
    const val okhttp3 = "3.7.0"
    const val mockito = "1.6.0"
    const val retrofit = "2.6.1"
    const val espresso = "3.2.0"
    const val koin_test = "1.0.0"
    const val appcompat = "1.2.0"
    const val test_junit = "1.1.1"
    const val pagination = "2.1.2"
    const val buildTools = "30.0.0"
    const val carouselView = "0.1.5"
    const val recyclerview = "1.1.0"
    const val supportLibrary = "1.3.1"
    const val recycler_actions = "3.0.2"
    const val constraintlayout = "1.1.3"
}

object Plugins {
    const val kotlin_android = "kotlin-android"
    const val kotlin_android_kapt = "kotlin-kapt"
    const val android_library = "com.android.library"
    const val android_application = "com.android.application"
    const val kotlin_android_extensions = "kotlin-android-extensions"
}

object Deps {
    const val junit = "junit:junit:${Versions.junit}"
    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val mockito = "com.nhaarman:mockito-kotlin:${Versions.mockito}"
    const val core_testing = "android.arch.core:core-testing:${Versions.arch}"
    const val build_gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    const val carousel_view = "com.synnapps:carouselview:${Versions.carouselView}"
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val kotlin_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val espresso_contrib = "com.android.support.test.espresso:espresso-contrib:${Versions.recycler_actions}"

    //Koin
    const val koin_core = "org.koin:koin-core:${Versions.koin}"
    const val koin_test = "org.koin:koin-test:${Versions.koin_test}"
    const val koin_viewmodel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"

    //Retrofit
    const val retrofit_core = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofit_rxjava = "com.squareup.retrofit2:adapter-rxjava2:${Versions.rxjava}"
    const val retrofit_converter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    //OkHttp3
    const val okhttp3_core = "com.squareup.okhttp3:okhttp:${Versions.okhttp3}"
    const val okhttp3_mockwebserver = "com.squareup.okhttp3:mockwebserver:${Versions.okhttp3}"

    //AndroidX
    const val androidx_test_rules = "androidx.test:rules:${Versions.testx}"
    const val androidx_test_runner = "androidx.test:runner:${Versions.testx}"
    const val androidx_ktx = "androidx.core:core-ktx:${Versions.supportLibrary}"
    const val androidx_test_junit = "androidx.test.ext:junit:${Versions.test_junit}"
    const val androidx_appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val androidx_pagination = "androidx.paging:paging-runtime:${Versions.pagination}"
    const val androidx_espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val androidx_recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val androidx_espresso_intents = "androidx.test.espresso:espresso-intents:${Versions.espresso}"
    const val androidx_constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
}