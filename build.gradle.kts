buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath (Deps.build_gradle)
        classpath (Deps.kotlin_plugin)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}