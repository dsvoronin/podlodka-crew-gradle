/**
 * Old school?
 */
object Deps {

    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:1.3.72"

    object jetpack {
        const val appCompat = "androidx.appcompat:appcompat:1.1.0"
        const val coreCtx = "androidx.core:core-ktx:1.3.0"
        const val material = "com.google.android.material:material:1.1.0"
        const val constraint = "androidx.constraintlayout:constraintlayout:1.1.3"
        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:2.3.0"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:2.3.0"
    }

    object test {
        const val junit = "junit:junit:4.13"
    }

    object androidTest {
        const val espresso = "androidx.test.espresso:espresso-core:3.2.0"
        const val junit = "androidx.test.ext:junit:1.1.1"
    }
}

fun library(key: String): String {
    return when (key) {
        "kotlinStdLib" -> "org.jetbrains.kotlin:kotlin-stdlib:1.3.72"
        "appcompat" -> "androidx.appcompat:appcompat:1.1.0"
        "material" -> "com.google.android.material:material:1.1.0"
        "constraint" -> "androidx.constraintlayout:constraintlayout:1.1.3"

        else -> error("Unregistered library key: $key, check buildSrc/Deps.kt")
    }
}
