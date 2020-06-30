import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.internal.tasks.PackageRenderscriptTask
import com.android.build.gradle.tasks.AidlCompile
import com.android.build.gradle.tasks.RenderscriptCompile
import com.android.build.gradle.tasks.ShaderCompile
import com.avito.android.plugin.build_param_check.CheckMode.FAIL
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

plugins {
    id("com.avito.android.buildchecks")
}

buildChecks {

    enableByDefault = true

    /**
     * Android build tools uses android.jar ($ANDROID_HOME/platforms/android-<compileSdkVersion>/android.jar).
     * The version can be specified only without a revision (#117789774). Different revisions lead to Gradle remote cache misses
     */
    androidSdk {
        compileSdkVersion = 30
        revision = 1
    }

    /**
     * The Java version can influence the output of the Java compiler. It leads to Gradle remote cache misses.
     */
    javaVersion {
        version = JavaVersion.VERSION_1_8
    }

    /**
     * If two Android modules use the same package, their R classes will be merged. While merging,
     * it can unexpectedly override resources. It happens even with android.namespacedRClass.
     */
    uniqueRClasses {
        enabled = false
    }

    /**
     * On macOs java.net.InetAddress#getLocalHost()
     * invocation can last up to 5 seconds instead of milliseconds (thoeni.io/post/macos-sierra-java).
     */
    macOSLocalhost { }

    /**
     * Dynamic versions, such as “2.+”, and snapshot versions force Gradle to check them on a remote server.
     * It slows down a configuration time and makes build less reproducible.
     */
    dynamicDependencies { }

    /**
     * Gradle can run multiple daemons for many reasons.
     * If you use buildSrc in the project with standalone Gradle wrapper, this check will verify common problems to reuse it.
     */
    gradleDaemon { }

    /**
     * This check verifies that all KAPT annotation processors support incremental annotation processing if it is enabled (kapt.incremental.apt=true).
     * Because if one of them does not support it then whole incremental annotation processing won’t work at all
     */
    incrementalKapt {
        mode = FAIL
    }
}

allprojects {

    @Suppress("UnstableApiUsage")
    repositories {
        jcenter()

        exclusiveContent {
            forRepository {
                google()
            }
            filter {
                includeGroupByRegex("androidx\\..+")
                includeGroupByRegex("com.android.*")
                includeGroupByRegex("com.google.android.+")
            }
        }
    }

    apply(from = "${rootDir}/dependencies.gradle")
}

subprojects {

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

            libraryVariants.all {
                // TODO: replace with https://issuetracker.google.com/issues/72050365 once released (4.0+)
                generateBuildConfigProvider?.configure {
                    enabled = false
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
