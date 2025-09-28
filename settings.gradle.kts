import java.io.FileInputStream
import java.util.Properties

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

var githubUsername: String
var githubToken: String

val localPropsFile = file("local.properties")
if (localPropsFile.exists()) {
    val props = Properties()
    props.load(FileInputStream(localPropsFile))
    githubUsername = props.getProperty("github.username")
    githubToken = props.getProperty("github.token")
} else {
    githubUsername = System.getenv("USERNAME") ?: ""
    githubToken = System.getenv("TOKEN") ?: ""
}

println("GithubUsername is: '$githubUsername'")
println("GithubToken is: '${githubToken.take(4)}...'")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://www.jitpack.io")
        maven {
            url = uri("https://maven.pkg.github.com/Dezdeqness/Android-Support-Things")
            credentials {
                username = githubUsername
                password = githubToken
            }
        }
    }
}

rootProject.name = "Fono"
include(":app")

include(":feature:auth")
include(":feature:likedtracks")
include(":feature:player")
include(":feature:home")
include(":feature:playlist")
include(":feature:settings")

include(":common:core-network")
include(":common:core")
include(":common:core-player")

include(":shared-data")
include(":shared-ui")

