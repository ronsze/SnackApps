plugins {
    alias(libs.plugins.snack.feature)
}

android {
    namespace = "kr.sdbk.clock"
}

dependencies {
    implementation(libs.kotlinx.datetime)
}