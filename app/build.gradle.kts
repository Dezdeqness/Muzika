plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.dezdeqness.muzika"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.dezdeqness.muzika"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "0.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(path = ":ui_kit"))

    implementation(platform(libs.compose.bom))

    implementation(libs.appcompat)

    implementation(libs.core.ktx)

    implementation(libs.lifecycle.runtime.ktx)

    implementation(libs.activity.compose)
    implementation(libs.ui)
    implementation(libs.ui.util)
    implementation(libs.ui.graphics)
    implementation(libs.foundation)
    implementation(libs.material3)
    implementation(libs.ui.tooling.preview)
    implementation(libs.compose.navigation)
    implementation(libs.coil)
    debugImplementation(libs.ui.tooling)

    implementation(libs.viewmodel.ktx)
    implementation(libs.viewmodel.compose)
    implementation(libs.viewmodel.runtime)
    implementation(libs.viewmodel.saved.state)

    implementation(libs.media3.exoplayer)
    implementation(libs.media3.ui)
    implementation(libs.media3.common)
    implementation(libs.media3.session)

    implementation(libs.ktor.core)
    implementation(libs.ktor.okhttp)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.ktor.serialization)
    implementation(libs.okhttp.logging)

}