plugins {
    id("com.android.application")
    id("kotlin-android")
    id("androidx.navigation.safeargs")
}

android {
    defaultConfig {
        applicationId = "com.example.myapplication"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
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
}

dependencies {
    implementation(project(":mylibrary"))
    implementation(project(":mylibrary2"))
    implementation(library("kotlinStdLib"))
    implementation(Deps.jetpack.appcompat)
    implementation(Deps.jetpack.coreCtx)
    implementation(Deps.jetpack.material)
    implementation(Deps.jetpack.constraint)
    implementation(Deps.jetpack.navigationFragment)
    implementation(Deps.jetpack.navigationUi)

    testImplementation(Deps.test.junit)

    androidTestImplementation(Deps.androidTest.junit)
    androidTestImplementation(Deps.androidTest.espresso)
}
