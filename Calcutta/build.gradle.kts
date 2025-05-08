// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        mavenCentral()
        google()
    }
    dependencies {
        // Remove redundant classpath declarations that conflict with plugins block
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.jetbrainsKotlinSerialization) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}