import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlinx.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.maven.publish)
}
group = "com.kdani.ktor.helper"

mavenPublishing {
    // Automatically configure publishing for all Kotlin Multiplatform targets
    configure(
        KotlinMultiplatform(
            javadocJar = JavadocJar.Empty(),
            sourcesJar = true,
            androidVariantsToPublish = listOf("release")
        )
    )
}

kotlin {
    applyDefaultHierarchyTemplate()
    jvm()
    iosX64()
    androidTarget {
        publishLibraryVariants("release")
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.ktor.client.core)
                api(libs.ktor.client.json)
                api(libs.ktor.client.serialization)
                api(libs.ktor.content.negotiation)
                api(libs.ktor.client.logging)
                implementation(libs.ktor.client.cio)
                implementation(libs.ktor.kotlinx.serialization)
                implementation(libs.coroutines.core)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(libs.ktor.client.ios)
            }
        }
    }
}

android {
    namespace = "com.kdani.ktor.helper"
    compileSdk = 34
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}