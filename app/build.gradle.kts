import com.android.build.gradle.tasks.registerDataBindingOutputs

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.withdignityfinal"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.withdignityfinal"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
    dataBinding.isEnabled=true

}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation(platform("com.google.firebase:firebase-bom:32.7.1"))
    implementation ("com.google.firebase:firebase-firestore-ktx")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth-ktx:22.3.1")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation("com.google.firebase:firebase-firestore:24.10.1")
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("androidx.compose.material3:material3-android:1.2.1")
    testImplementation("junit:junit:4.13.2")
    implementation ("com.firebaseui:firebase-ui-database:8.0.1")
    implementation ("com.google.firebase:firebase-bom:32.7.1")
    implementation ("com.google.firebase:firebase-database-ktx")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.25")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation ("com.github.bumptech.glide:glide:4.14.2")
    implementation ("com.squareup.picasso:picasso:2.5.2")
    implementation ("com.github.bumptech.glide:glide:4.14.2")
    implementation ("com.google.firebase:firebase-firestore:24.10.1")
    implementation ("com.google.firebase:firebase-core:21.1.1")
    implementation ("com.firebaseui:firebase-ui-firestore:8.0.0")
    implementation ("com.google.firebase:firebase-firestore:21.7.1") // Use the latest version
    implementation ("com.google.firebase:firebase-core:16.0.1") // Include Firebase Core
    implementation ("com.firebaseui:firebase-ui-firestore:7.2.0") // Use the latest version
    implementation ("com.github.flutterwave.rave-android:rave_android:2.1.39")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.14.2")

}