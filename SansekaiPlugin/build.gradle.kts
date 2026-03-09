plugins {
    id("com.android.library")
    kotlin("android")
    id("com.lagradost.cloudstream3.gradle")
}

android {
    namespace = "com.querhen.sansekai"
    compileSdk = 33
    defaultConfig { minSdk = 21 }
}

cloudstream {
    setPath("com.querhen.Sansekai")
    description = "Nonton Drama & Anime gratis dari Sansekai API"
}
