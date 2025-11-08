import com.android.build.api.dsl.LibraryExtension
import configuration.configureKotlinAndroid
import configuration.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class FeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.library")
            apply(plugin = "org.jetbrains.kotlin.android")
            apply(plugin = "snack.compose")
            apply(plugin = "snack.hilt")

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                defaultConfig.consumerProguardFiles("consumer-rules.pro")
            }

            dependencies {
                "implementation"(project(":core:ui"))
                "implementation"(project(":core:design-system"))
                "implementation"(project(":core:android-extensions"))

                "implementation"(libs.findLibrary("androidx-core-ktx").get())
                "implementation"(libs.findLibrary("androidx-appcompat").get())
                "implementation"(libs.findLibrary("material").get())
                "implementation"(libs.findLibrary("androidx-lifecycle-runtime-ktx").get())
            }
        }
    }
}