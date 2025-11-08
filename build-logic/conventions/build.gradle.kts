import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.java
import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("application") {
            id = libs.plugins.snack.application.get().pluginId
            implementationClass = "ApplicationConventionPlugin"
        }

        register("feature") {
            id = libs.plugins.snack.feature.get().pluginId
            implementationClass = "FeatureConventionPlugin"
        }

        register("library") {
            id = libs.plugins.snack.library.get().pluginId
            implementationClass = "LibraryConventionPlugin"
        }

        register("compose") {
            id = libs.plugins.snack.compose.get().pluginId
            implementationClass = "ComposeConventionPlugin"
        }

        register("hilt") {
            id = libs.plugins.snack.hilt.get().pluginId
            implementationClass = "HiltConventionPlugin"
        }
    }
}