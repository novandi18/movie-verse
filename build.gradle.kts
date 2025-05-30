buildscript {
    dependencies {
        classpath(libs.android.secrets.gradle)
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.dagger.hilt) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.secrets.gradle.plugin) apply false
    alias(libs.plugins.compose.compiler) apply false
}

println("")