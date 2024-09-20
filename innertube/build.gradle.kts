plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.ktor.core)
    implementation(libs.ktor.okhttp)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.ktor.serialization)
    implementation(libs.okhttp.logging)
}
