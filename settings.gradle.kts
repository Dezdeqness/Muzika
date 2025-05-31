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
        maven("https://www.jitpack.io")
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

rootProject.name = "Aqua"
include(":app")
include(":feature:auth")
include(":feature:likedtracks")
include(":common:core-network")
include(":shared-data")
include(":shared-ui")

