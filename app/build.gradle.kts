plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    // firebase plugin
    id("com.google.gms.google-services")

    //hilt plugin
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    kotlin("kapt")

    //room id
    id("com.google.devtools.ksp")

}

android {
    namespace = "com.example.pruebat"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.pruebat"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.1"

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
    viewBinding{
        enable = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(11))
        }
    }
    kapt {
        correctErrorTypes = true
    }

}

dependencies {

    implementation ("androidx.activity:activity-ktx:1.7.2")

    val lifecycle_version = "2.6.1"


    //firebase bom
    implementation(platform("com.google.firebase:firebase-bom:32.2.2"))
    //firebase auth
    implementation("com.google.firebase:firebase-auth-ktx")
    //firestore
    implementation("com.google.firebase:firebase-firestore-ktx")

    //View Model dependencies
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

    //hilt
    implementation ("com.google.dagger:hilt-android:2.47")
    kapt("com.google.dagger:hilt-android-compiler:2.47")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    //glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")



    //room dependencie
    implementation("androidx.room:room-runtime:2.5.2")
    ksp("androidx.room:room-compiler:2.5.2")
    implementation("androidx.room:room-ktx:2.5.2")

    //Lottie dependency
    implementation ("com.airbnb.android:lottie:6.1.0")

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}