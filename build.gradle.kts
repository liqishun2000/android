// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.compose.compiler) apply false

    id("org.jetbrains.kotlin.plugin.serialization") version("2.3.20")
    id("com.google.devtools.ksp") version "2.3.6" apply false
    alias(libs.plugins.hilt) apply false
}