import org.jetbrains.compose.desktop.application.dsl.TargetFormat


plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

group = "com.claaj"
version = "1.0-SNAPSHOT"

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
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation(compose.materialIconsExtended)
    implementation("org.jetbrains.kotlinx:dataframe:0.11.1")
    implementation("org.jetbrains.kotlinx:dataframe-excel:0.11.1")
    testImplementation(kotlin("test"))
}

compose.desktop {
    application {
        mainClass = "ui.MainKt"

        nativeDistributions {
            modules("jdk.unsupported")
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            targetFormats(TargetFormat.Rpm)
            packageName = "Konci"
            packageVersion = "1.0.0"
            description = "Ayudante para conciliar percepciones y retenciones."
            copyright = "© 2023 Cajal Matías. All rights reserved."
            // licenseFile.set(project.file("LICENSE.txt"))

            linux {
                rpmLicenseType = "MIT"
                rpmPackageVersion = "1.0.0"
                debPackageVersion = "1.0.0"
                appRelease = "1"
                appCategory = "Work"
                iconFile.set(project.file("resources/icon.png"))
            }

            windows {
                console = false
                iconFile.set(project.file("resouces/icon.ico"))
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

