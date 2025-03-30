plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Firebase
    alias(libs.plugins.googleServices)
}

android {
    namespace = "com.example.zventapp_tiendavirtual"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.zventapp_tiendavirtual"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.lottie) /*Animaciones*/
    implementation(libs.firebaseAuth) /*Autenticación con Firebase*/
    implementation(libs.firebaseDatabase) /*Base de datos con Firebase*/
    implementation(libs.imagePicker) /* Recortar una imagen*/
    implementation(libs.glide) /*Cargar Imagenes*/
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}