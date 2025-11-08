plugins {
    alias(libs.plugins.snack.library)
}

android {
    namespace = "kr.sdbk.android_extensions"
}

dependencies {
    implementation(libs.androidx.core.ktx)
}