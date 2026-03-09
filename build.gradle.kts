plugins {
    id("com.lagradost.cloudstream3.gradle") version "6.0.2" apply false
    kotlin("android") version "1.9.22" apply false
}

// Tidak perlu blok buildscript atau allprojects lagi karena sudah di-handle settings.gradle.kts
