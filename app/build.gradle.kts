plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.smlab.zapride"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.smlab.zapride"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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

    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // SDP Android
    implementation ("com.intuit.sdp:sdp-android:1.1.0")
    // Country Code Picker
    implementation ("com.github.vardemin.MaterialCountryPicker:materialcountrypicker:0.2.3")
    // Google Map
    implementation ("com.google.android.gms:play-services-maps:19.0.0")
    implementation ("com.google.android.gms:play-services-location:21.3.0")
    // Circle Image View
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    // Google Places Name
    implementation ("com.google.android.libraries.places:places:2.7.0")
    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.9.0"))
    implementation("com.google.firebase:firebase-auth-ktx")
    // Country Code Parser
    implementation ("com.googlecode.libphonenumber:libphonenumber:8.13.15")
    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
}