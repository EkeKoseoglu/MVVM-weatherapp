import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    extra["kotlin_version"] = "1.3.11"

    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.2.1")
        classpath(kotlin("gradle-plugin", "${extra["kotlin_version"]}"))
        classpath("android.arch.navigation:navigation-safe-args-gradle-plugin:1.0.0-alpha09")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}


allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}
tasks.withType<KotlinCompile>{
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
