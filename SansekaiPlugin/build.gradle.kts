import com.lagradost.cloudstream3.gradle.CloudstreamExtension

plugins {
    id("com.android.library")
    kotlin("android")
    id("com.lagradost.cloudstream3.gradle")
}

android {
    namespace = "com.querhen.sansekai"
    compileSdk = 33
    defaultConfig {
        minSdk = 21
    }
}

cloudstream {
    // Pastikan ini merujuk ke class utama di Sansekai.kt
    setPath("com.querhen.Sansekai")
    description = "Nonton Drama & Shortmax via Sansekai API"
}
