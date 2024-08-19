plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.di.hilt.android)
}

android {
    namespace = "me.kofesst.lovemood"
    compileSdk = 34

    defaultConfig {
        applicationId = "me.kofesst.lovemood"
        minSdk = 28
        targetSdk = 34
        versionCode = 2
        versionName = "0.0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
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
}

dependencies {
    // Datastore
    implementation(libs.datastore.preferences)
    implementation(libs.datastore.preferences.core)

    // Room ORM
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)

    // Shimmer effects
    implementation(libs.shimmer.compose)

    // Lottie
    implementation(libs.lottie.compose)

    // Google fonts
    implementation(libs.androidx.googlefonts)

    // DI
    implementation(libs.di.hilt.android)
    implementation(libs.di.hilt.navigation)
    kapt(libs.di.hilt.compiler.android)
    kapt(libs.di.hilt.compiler.androidx)

    // Modules
    implementation(project(":app-async"))
    implementation(project(":app-navigation"))
    implementation(project(":features-validation"))
    implementation(project(":features-date"))
    implementation(project(":datastore"))
    implementation(project(":database"))
    implementation(project(":core-ui"))
    implementation(project(":core"))

    // Androidx
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.splashscreen)
    implementation(libs.androidx.constraintlayout.compose)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.android.material)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.icons)

    // Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}