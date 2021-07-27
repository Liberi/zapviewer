buildscript {
    val kotlin_version by extra("1.3.72")
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath (Deps.build_gradle)
        classpath (Deps.kotlin_plugin)
        classpath ("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.14.2")
        "classpath"("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
    
    apply {
        from("$rootDir/detekt.gradle")
    }

}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}