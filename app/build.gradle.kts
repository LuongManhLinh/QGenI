plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.chaquo.python")

}

android {
    namespace = "com.example.qgeni"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.qgeni"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        ndk {
            abiFilters += listOf("arm64-v8a", "x86_64")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }


}

chaquopy {
    defaultConfig {
        pip {
            install("gTTS")
        }
    }

    sourceSets {
        getByName("main") {
            srcDir("src/main/python")
        }
    }

}


dependencies {
    implementation ("androidx.datastore:datastore-preferences:1.0.0")


    implementation("androidx.navigation:navigation-compose:2.7.4")

    implementation ("androidx.compose.material3:material3:1.2.0")
    implementation ("androidx.compose.material3:material3-window-size-class:1.2.0")

    implementation ("com.airbnb.android:lottie-compose:6.1.0")


    // Icons (Extended)
    implementation ("androidx.compose.material:material-icons-extended:1.5.1")

    implementation("org.mongodb:mongo-java-driver:3.12.7")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.coil.compose)
    implementation(libs.generativeai)
    implementation(libs.core.ktx)
    implementation(libs.androidx.navigation.testing)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    testImplementation("io.mockk:mockk:1.13.5")
    testImplementation ("org.robolectric:robolectric:4.10.3")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0")
}