import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import configuration.libs
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.findByType

class ComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.jetbrains.kotlin.plugin.compose")
            apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

            dependencies {
                val bom = libs.findLibrary("compose-bom").get()
                "implementation"(platform(bom))
                "implementation"(libs.findBundle("compose").get())
                "implementation"(libs.findLibrary("kotlin-serialization-json").get())
            }

            val android = when {
                plugins.hasPlugin("com.android.application") -> extensions.findByType<ApplicationExtension>()
                plugins.hasPlugin("com.android.library") -> extensions.findByType<LibraryExtension>()
                else -> throw GradleException("This plugin should be applied to either an application or library module")
            }

            android?.apply {
                buildFeatures {
                    compose = true
                }

                testOptions {
                    unitTests {
                        isIncludeAndroidResources = true
                    }
                }
            }
        }
    }
}