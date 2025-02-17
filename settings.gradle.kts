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

rootProject.name = "Tasks Splitter"
include(":app")
include(":features")
include(":common")
include(":features:start")
include(":features:auth")
include(":features:main")
include(":features:main:my_groups")
include(":features:main:my_tasks")
include(":features:main:account")
