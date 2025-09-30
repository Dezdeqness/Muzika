plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.dezdeqness.muzika"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.dezdeqness.muzika"
        minSdk = 24
        targetSdk = 35

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val versionNameFromEnv = (System.getenv("VERSION_NAME") ?: "1.0.0").removePrefix("v")
        versionName = versionNameFromEnv

        val (major, minor, patch) = versionNameFromEnv
            .split(".")
            .map { it.toInt() }
            .let { Triple(it[0], it[1], it[2]) }

        println("Version is $versionName")
        println("Major: $major")
        println("Minor: $minor")
        println("Patch: $patch")

        versionCode = major * 10_000_000 + minor * 100_000 + patch * 1_000
        println("Version code: $versionCode")
    }

    signingConfigs {
        create("release") {
            storeFile = file(System.getenv("KEYSTORE_FILE") ?: return@create)
            storePassword = System.getenv("KEYSTORE_PASSWORD")
            keyAlias = System.getenv("KEY_ALIAS")
            keyPassword = System.getenv("KEY_PASSWORD")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }

        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
            applicationIdSuffix = ".debug"
        }

        create("qa") {
            isMinifyEnabled = true
            isDebuggable = false
            applicationIdSuffix = ".test"
            matchingFallbacks.add("debug")
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.toString()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("room.incremental", "true")
    arg("room.expandProjection", "true")
    arg("KOIN_CONFIG_CHECK", "true")
}

dependencies {

    implementation(platform(libs.compose.bom))
    api(libs.appcompat)

    api(libs.core.ktx)
    implementation(project(":feature:auth"))
    implementation(project(":feature:player"))
    implementation(project(":feature:likedtracks"))
    implementation(project(":feature:home"))
    implementation(project(":feature:playlist"))
    implementation(project(":feature:settings"))

    implementation(project(":shared-data"))
    implementation(project(":shared-ui"))

    implementation(project(":common:core-network"))
    implementation(project(":common:core"))
    implementation(project(":common:core-ui"))

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
    implementation(libs.coil.core)
    implementation(libs.coil.network)
    debugImplementation(libs.ui.tooling)

    implementation(libs.viewmodel.ktx)
    implementation(libs.viewmodel.compose)
    implementation(libs.viewmodel.runtime)
    implementation(libs.viewmodel.saved.state)

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.kotlinx.serialization)

    implementation(libs.core.ui)
    implementation(libs.koin.annotations)
    ksp(libs.koin.compiler)
    implementation(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.viewmodel)
    implementation(libs.koin.android)
    implementation(project.dependencies.platform(libs.koin.bom))
}
