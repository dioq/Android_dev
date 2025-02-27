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
include(":broadcastreceiver1")
include(":broadcastreceiver2")
include(":button1")
include(":checkbox1")
include(":checkbox2")
include(":custonapplication")
include(":dialogdemo")
include(":imageview1")
include(":imageview2")
include(":json_parse")
include(":judge_root")
include(":layout01")
include(":listview1")
include(":listview2")
include(":listview3")
include(":log_study")
include(":network_native")
include(":okhttp_util")
include(":okhttp1")
include(":package_install")
include(":permission01")
include(":phone")
include(":photograph")
include(":proxy_dynamic")
include(":proxy_local")
include(":radio")
include(":resource_manager")
include(":sandbox_study")
include(":scaledpixels")
include(":sensor01")
include(":service_bindservice")
include(":service_startservice")
include(":service1")
include(":sharedpreferences1")
include(":tabdemo")
include(":thread1")
include(":timertask01")
include(":viewdemo1")
include(":viewdemo2")
include(":viewdemo3")
include(":webview01")
include(":clipboard_study")
include(":constraintlayout01")
