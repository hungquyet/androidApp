plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.callapitourdulich"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.callapitourdulich"
        minSdk = 30
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
}

dependencies {

    // AndroidX libraries
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("androidx.recyclerview:recyclerview:1.3.0") // RecyclerView
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.0") // ViewModel
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.0") // LiveData

    // Retrofit for network calls
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // Glide for image loading
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    // Coroutines for asynchronous tasks (if needed)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Testing libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Optional: Timber for logging (useful for debugging)
    implementation("com.jakewharton.timber:timber:5.0.1")
}
