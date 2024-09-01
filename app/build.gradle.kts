plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.chaquopy)
}

android {
    namespace = "com.example.signlanguagedetector"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.signlanguagedetector"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        ndk {
            abiFilters += listOf("arm64-v8a", "armeabi-v7a", "arm64-v8a", "x86", "x86_64")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        mlModelBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

chaquopy {
    defaultConfig {
//        productFlavors {
//            getByName("py310") { version = "3.10" }
//        }
         version = "3.8"

        pip {
            install("numpy")
        }
    }
}

dependencies {
    implementation("org.tensorflow:tensorflow-lite:2.9.0")
    implementation("org.tensorflow:tensorflow-lite-task-vision:0.3.1")
    implementation("org.tensorflow:tensorflow-lite-gpu:2.9.0")
    implementation(libs.vision.common)
//    implementation(libs.tensorflow.lite.support)
    implementation(libs.tensorflow.lite.metadata)
    implementation(libs.androidx.navigation.compose)

    val cameraxVersion = "1.3.1";
    implementation ("androidx.camera:camera-core:${cameraxVersion}")
    implementation ("androidx.camera:camera-camera2:${cameraxVersion}")
    implementation ("androidx.camera:camera-view:${cameraxVersion}")
    implementation ("androidx.camera:camera-lifecycle:$cameraxVersion")

    implementation("com.google.accompanist:accompanist-permissions:0.35.1-alpha")

    implementation("com.google.mediapipe:tasks-vision:latest.release")

    implementation("io.github.kevinnzou:compose-webview:0.33.6")
//    implementation("com.google.mlkit:pose-detection:18.0.0-beta5")
//    implementation("com.google.mlkit:face-detection:16.1.7")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}