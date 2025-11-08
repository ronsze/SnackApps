import configuration.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.google.devtools.ksp")
            apply(plugin = "com.google.dagger.hilt.android")

            dependencies {
                "implementation"(libs.findLibrary("hilt").get())
                "ksp"(libs.findLibrary("hilt-compiler").get())
            }

            pluginManager.withPlugin("snack.compose") {
                dependencies {
                    "implementation"(libs.findLibrary("hilt-navigation-compose").get())
                }
            }
        }
    }
}