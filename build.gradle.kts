import org.jetbrains.compose.desktop.application.dsl.TargetFormat


plugins {
    kotlin("jvm") version "1.9.20"
    id("org.jetbrains.compose") version "1.5.10"
    id("org.jetbrains.kotlinx.dataframe") version "0.12.0"
}

group = "com.claaj"
version = "0.0.1-BETA"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs) {
        exclude(group = "org.jetbrains.compose.material")
    }
    implementation(compose.material3)
    implementation(compose.materialIconsExtended)
    implementation("org.jetbrains.kotlinx:dataframe:0.12.0")
    implementation("org.jetbrains.kotlinx:dataframe-excel:0.12.0")
    implementation("org.apache.logging.log4j:log4j-api:2.20.0")
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(17)
}

compose.desktop {
    application {
        mainClass = "com.github.claaj.konci.ui.MainKt"

        nativeDistributions {
            includeAllModules = true
            //modules("jdk.unsupported")
            appResourcesRootDir.set(project.layout.projectDirectory.dir("resources"))

            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            targetFormats(TargetFormat.Rpm)
            packageName = "Konci"
            packageVersion = "0.0.1"
            description = "Ayudante para conciliar percepciones y retenciones."
            copyright = "© 2023 Cajal Matías. All rights reserved."
            licenseFile.set(project.file("LICENSE"))

            linux {
                rpmLicenseType = "MIT"
                rpmPackageVersion = "0.0.1"
                debPackageVersion = "0.0.1"
                appRelease = "1"
                appCategory = "Work"
                iconFile.set(project.file("resources/icon.png"))
            }

            windows {
                console = false
                iconFile.set(project.file("resources/icon.ico"))
            }
        }
        buildTypes.release.proguard {
            configurationFiles.from(project.file("compose-desktop.pro"))
        }
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
