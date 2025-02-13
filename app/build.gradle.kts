plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.pacepocfigma"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.pacepocfigma"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        compose = true
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)  // Material components
    implementation(project(":uilibrary"))  // Your custom UI library

    // Jetpack Compose libraries
    implementation(libs.androidx.compose.ui)  // Core Compose UI
    implementation(libs.androidx.compose.material)  // Material Compose
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.compose.activity)  // Activity Compose for Compose-specific activity functionality
    implementation(libs.androidx.compose.tooling)  // Compose Tooling for better support in development

    // Image Loading (using Coil)
    implementation(libs.coil.compose)

    // Testing libraries
    testImplementation(libs.junit)  // Unit tests
    androidTestImplementation(libs.androidx.junit)  // UI tests with JUnit
    androidTestImplementation(libs.androidx.espresso.core)  // Espresso for UI tests

}