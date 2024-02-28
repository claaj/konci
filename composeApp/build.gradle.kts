import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform) version "1.9.20"
    alias(libs.plugins.jetbrainsCompose) version "1.5.10"
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
            implementation(compose.material3)
            implementation(compose.ui)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(compose.desktop.currentOs) {
                exclude(group = "org.jetbrains.compose.material")
            }
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation("org.jetbrains.kotlinx:dataframe:0.12.0")
            implementation("org.jetbrains.kotlinx:dataframe-excel:0.12.0")
            implementation("com.darkrockstudios:mpfilepicker:3.1.0")
            // PreCompose
            api(compose.foundation)
            api(compose.animation)
            api("moe.tlaster:precompose:1.5.10")
            api("moe.tlaster:precompose-viewmodel:1.5.10")
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

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

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
