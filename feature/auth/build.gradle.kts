plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    id("de.jensklingenberg.ktorfit") version "2.2.0"
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.dezdeqness.auth"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.toString()
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.bundles.ktor.common)
    implementation(libs.bundles.ktorfit.common)
    debugImplementation(libs.androidx.ui.tooling)
    ksp(libs.ktorfit.ksp)
    implementation(libs.okhttp.logging)
    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.viewmodel)
    implementation(libs.koin.android)
    implementation(libs.ui)
    implementation(libs.ui.util)
    implementation(libs.ui.graphics)
    implementation(libs.foundation)
    implementation(libs.material3)
    implementation(libs.ui.tooling.preview)
    implementation(platform(libs.compose.bom))
    implementation(libs.activity.compose)
    implementation(libs.androidx.browser)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.corutines)
    testImplementation(libs.junit)

    implementation(project(":common:core-network"))

    androidTestImplementation(libs.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}