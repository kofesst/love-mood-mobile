pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "LoveMoodMobile"
include(":app")
include(":database")
include(":core")
include(":features-date")
include(":core-ui")
include(":features-validation")
include(":datastore")
include(":app-navigation")
include(":app-widgets")
include(":app-async")
include(":app-localization")
