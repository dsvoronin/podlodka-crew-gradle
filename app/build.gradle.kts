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

            // ВОТ ТУТ ВЫКЛЮЧАЕТСЯ PROGUARD/R8, Артем
            // gradle.properties / android.useR8=true/false для определения proguard или r8
            // Да, Артем, они совместимы почти полностью
            // что работает что нет, можно посмотреть в логе таски при сборке,
            // там будет ворнинг что правило не знакомо r8
            // Кстати в последних версиях AS появилась подсветка синтаксиса proguard!
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

apply(from = "$projectDir/dependencies.gradle")

//dependencies {
//    implementation(project(":mylibrary"))
//    implementation(project(":mylibrary2"))
//
//    implementation(library("kotlinStdLib"))
//
//    implementation(Deps.jetpack.appcompat)
//    implementation(Deps.jetpack.coreCtx)
//    implementation(Deps.jetpack.material)
//    implementation(Deps.jetpack.constraint)
//    implementation(Deps.jetpack.navigationFragment)
//    implementation(Deps.jetpack.navigationUi)
//
//    testImplementation(Deps.test.junit)
//
//    androidTestImplementation(Deps.androidTest.junit)
//    androidTestImplementation(Deps.androidTest.espresso)
//}
