plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    id("de.jensklingenberg.ktorfit") version "2.2.0"
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.dezdeqness.playlist"
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
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

ksp {
    arg("KOIN_CONFIG_CHECK","true")
}

dependencies {
    implementation(libs.bundles.ktor.common)
    implementation(libs.bundles.ktorfit.common)
    ksp(libs.ktorfit.ksp)
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.compose.navigation)
    implementation(libs.ui)
    implementation(libs.ui.util)
    implementation(libs.ui.graphics)
    implementation(libs.foundation)
    implementation(libs.material3)
    implementation(libs.ui.tooling.preview)
    implementation(platform(libs.compose.bom))
    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.annotations)
    ksp(libs.koin.compiler)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.viewmodel)
    implementation(libs.koin.android)
    implementation(project(":common:core-network"))
    implementation(project(":common:core"))
    implementation(project(":common:core-player"))
    implementation(project(":shared-data"))
    implementation(project(":shared-ui"))
    implementation(libs.coil)
    implementation(libs.core.ui)
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)
    implementation(libs.viewmodel.saved.state)
}
