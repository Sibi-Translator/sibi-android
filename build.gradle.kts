// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.chaquopy) apply false
    alias(libs.plugins.android.lib) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}
//
//android {
//    compileSdkVersion = "android-34"
//    namespace = "com.mnr.sla"
//}