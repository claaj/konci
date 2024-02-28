plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.jetbrainsCompose) version "1.5.10" apply false
    alias(libs.plugins.kotlinMultiplatform) version "1.9.20" apply false
}
