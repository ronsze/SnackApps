import com.android.build.api.dsl.ApplicationExtension
import configuration.configureKotlinAndroid
import configuration.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class ApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.application")
            apply(plugin = "org.jetbrains.kotlin.android")
            apply(plugin = "snack.compose")
            apply(plugin = "snack.hilt")

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            dependencies {
                "implementation"(project(":core:ui"))
                "implementation"(project(":core:design-system"))

                rootDir.resolve("features").listFiles()
                    ?.filter { it.isDirectory && file("${it.path}/build.gradle.kts").exists() }
                    ?.forEach { "implementation"(project(":features:${it.name}")) }

                "implementation"(libs.findLibrary("androidx-core-ktx").get())
                "implementation"(libs.findLibrary("androidx-appcompat").get())
                "implementation"(libs.findLibrary("material").get())
                "implementation"(libs.findLibrary("androidx-lifecycle-runtime-ktx").get())
            }
        }
    }
}