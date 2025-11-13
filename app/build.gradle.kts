plugins {
    alias(libs.plugins.snack.application)
}

android {
    namespace = "kr.sdbk.snackapps"

    defaultConfig {
        applicationId = "kr.sdbk.snackapps"
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        debug {

        }
        
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}