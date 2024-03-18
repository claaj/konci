import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
}

repositories {
    mavenCentral()
    google()
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
}

kotlin {
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(libs.dataframe)
            implementation(libs.dataframe.excel)
            implementation(libs.mpfilepicker)
            implementation(libs.log4j.api)
            implementation(libs.log4j.core)

            // PreCompose
            api(compose.foundation)
            api(compose.animation)
            api(libs.precompose)
            api(libs.precompose.viewmodel)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs) {
                exclude(group = "org.jetbrains.compose.material")
            }
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.github.claaj.konci.MainKt"

        buildTypes.release.proguard.configurationFiles.from(project.file("proguard-rules.pro"))

        nativeDistributions {
            modules("jdk.unsupported")
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Rpm)
            packageName = "konci"
            packageVersion = "1.0.0"
            description = "Ayudante para conciliar percepciones y retenciones."
            copyright = "© 2024 Cajal Matías. All rights reserved."
            licenseFile.set(project.file("../LICENSE"))

            linux {
                rpmLicenseType = "MIT"
                appRelease = "1"
                appCategory = "Work"
                iconFile.set(project.file("../resources/icon.png"))
            }

            windows {
                console = false
                iconFile.set(project.file("../resources/icon.ico"))
                shortcut = true
            }
        }
    }
}

configurations.all {
    attributes {
        // https://github.com/JetBrains/compose-jb/issues/1404#issuecomment-1146894731
        attribute(Attribute.of("ui", String::class.java), "awt")
    }
}
