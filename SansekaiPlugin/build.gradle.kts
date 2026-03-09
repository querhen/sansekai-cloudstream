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
    // Ini akan otomatis membuat file .cs3 yang bisa dibaca aplikasi
    setPath("com.querhen.Sansekai") 
    description = "Nonton Drama China, Shortmax, dan Anime gratis dari Sansekai API"
}
