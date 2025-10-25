package configuration

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlinAndroid(
    commonExtensions: CommonExtension<*, *, *, *, *, *>
) {
    commonExtensions.apply {
        compileSdk = 36

        defaultConfig.apply {
            minSdk = 28
            testInstrumentationRunner = "androidx.test.runner.AndroidJunitRunner"
        }

        compileOptions.apply {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }

        tasks.withType<KotlinCompile>().configureEach {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_11)
            }
        }
    }
}