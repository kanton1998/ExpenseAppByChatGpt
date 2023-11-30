plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.expenseappbychatgpt"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.expenseappbychatgpt"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("androidx.compose.foundation:foundation:1.0.5")
    implementation("androidx.compose.material:material:1.2.0")
    implementation("androidx.compose.material3:material3:1.0.5")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.0.5")
    implementation("androidx.compose.runtime:runtime-livedata:1.0.5")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.21")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.21")
    implementation("androidx.activity:activity-ktx:1.4.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.room:room-common:2.6.0")
    implementation("androidx.room:room-ktx:2.5.0")
    kapt("androidx.room:room-compiler:2.5.0")
}