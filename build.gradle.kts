import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.tasks.wrapper.Wrapper.DistributionType.ALL

buildscript {
    val kotlinVersion = "1.3.72"

    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:3.6.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.0")
    }
}

allprojects {
    repositories {

        google()
        jcenter()
    }
}

subprojects {

    apply(from = "${rootDir}/dependencies.gradle")

    plugins.matching { it is AppPlugin || it is LibraryPlugin }.whenPluginAdded {

        configure<BaseExtension> {

            setCompileSdkVersion(30)
            buildToolsVersion = "30.0.0"

            defaultConfig {
                minSdkVersion(16)
                targetSdkVersion(30)
            }
        }
    }

    plugins.withType<AppPlugin> {
        configure<AppExtension> {
            buildTypes {
                getByName("debug") {
                    setMatchingFallbacks("release")
                }
            }
        }
    }

    plugins.withType<LibraryPlugin> {

        configure<LibraryExtension> {
            variantFilter {
                if (name == "debug") {
                    setIgnore(true)
                }
            }
        }
    }
}

/**
 * https://docs.gradle.org/current/userguide/gradle_wrapper.html
 */
tasks.withType<Wrapper> {
    distributionType = ALL
    gradleVersion = "6.5"
}
