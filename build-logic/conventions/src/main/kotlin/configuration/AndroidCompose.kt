package configuration

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    apply(plugin = "org.jetbrains.kotlin.plugin.compose")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

    dependencies {
        val bom = libs.findLibrary("compose-bom").get()
        "implementation"(platform(bom))
        "implementation"(libs.findBundle("compose").get())
        "implementation"(libs.findLibrary("kotlin-serialization-json").get())
    }

    commonExtension.apply {
        buildFeatures {
            compose = true
        }
    }
}