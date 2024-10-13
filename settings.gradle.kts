import java.io.FileInputStream
import java.util.Properties

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

val props = Properties()
props.load(FileInputStream("local.properties"))

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/Dezdeqness/Android-Support-Things")
            credentials {
                username = props["github.username"].toString()
                password = props["github.token"].toString()
            }
        }
        maven {
            url = uri("https://maven.pkg.github.com/Dezdeqness/Pod")
            credentials {
                username = props["github.username"].toString()
                password = props["github.token"].toString()
            }
        }
    }
}

rootProject.name = "Muzika"
include(":app")
include(":innertube")
