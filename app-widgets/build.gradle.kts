plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.di.hilt.android)
}

android {
    namespace = "me.kofesst.lovemood.widgets"
    compileSdk = 34

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
}

dependencies {
    // Glance widget
    implementation(libs.glance)
    implementation(libs.glance.appwidget)
    implementation(libs.glance.material3)

    // DI
    implementation(libs.di.hilt.android)
    implementation(libs.di.hilt.worker)
    kapt(libs.di.hilt.compiler.android)
    kapt(libs.di.hilt.compiler.androidx)

    // Modules
    implementation(project(":app-localization"))
    implementation(project(":features-date"))
    implementation(project(":core-ui"))
    implementation(project(":core"))

    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.worker)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)

    // Tests
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}