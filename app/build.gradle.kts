plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.baghdad.tudee"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.baghdad.tudee"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    testCoverage {
        jacocoVersion = "0.8.11"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    lint {
        disable.add("NullSafeMutableLiveData")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)


    //Kotlinx-date-time
    implementation(libs.kotlinx.datetime)

    //Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)


    //material 3
    implementation(libs.material3)
    implementation(libs.ui)
    implementation(libs.androidx.runtime)


    // Koin core and Android
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.core)

    //coil
    implementation(libs.coil.compose)
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.kotlinx.coroutines.core)

   //Android-specific coroutine support (for Dispatchers.Main, etc.)
    implementation(libs.kotlinx.coroutines.android)

    // Room testing
    testImplementation(libs.androidx.room.testing)

    // Google Truth
    testImplementation (libs.truth)
    androidTestImplementation(libs.truth)

    // MockK
    testImplementation(libs.mockk)
    testImplementation(libs.mockk.android)

    // Coroutines testing
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.kotlinx.coroutines.test)
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}
