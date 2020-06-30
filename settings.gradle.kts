rootProject.name = "My Application"

include(":app")
include(":mylibrary")
include(":mylibrary2")

pluginManagement {
    repositories {
        maven {
            name = "Avito bintray"
            setUrl("https://dl.bintray.com/avito/maven")
        }
    }
    resolutionStrategy {
        eachPlugin {
            val pluginId = requested.id.id
            if (pluginId.startsWith("com.avito.android")) {
                val artifact = pluginId.replace("com.avito.android.", "")
                useModule("com.avito.android:$artifact:2020.10")
            }
        }
    }
}
