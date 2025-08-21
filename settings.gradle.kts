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

rootProject.name = "Android_dev"
include(":activity1")
include(":custonapplication")
include(":json_parse")
include(":judge_root")
include(":log_study")
include(":network_native")
include(":okhttp1")
include(":package_install")
include(":permission01")
include(":phone")
include(":photograph")
include(":proxy_dynamic")
include(":proxy_local")
include(":resource_manager")
include(":sandbox_study")
include(":sensor01")
include(":service_bindservice")
include(":service_startservice")
include(":service1")
include(":sharedpreferences1")
include(":thread1")
include(":timertask01")
include(":clipboard_study")
include(":cli")
include(":selectfile")
include(":parseapk")
include(":appinfo")
include(":selectfile2")
