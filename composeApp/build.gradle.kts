import org.gradle.api.JavaVersion.VERSION_22
import org.gradle.kotlin.dsl.android
import org.gradle.kotlin.dsl.commonMain
import org.gradle.kotlin.dsl.commonTest
import org.gradle.kotlin.dsl.compose
import org.gradle.kotlin.dsl.configureEach
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.implementation
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.jvm
import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.compose.reload.ComposeHotRun
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import kotlin.text.set

plugins {
    // 1) Kotlin Multiplatform
    alias(libs.plugins.kotlinMultiplatform)

    // 2) Compose Multiplatform
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

    // 3) SQLDelight MPP
    id("app.cash.sqldelight") version "2.1.0"

    // 4) Application Android
    alias(libs.plugins.androidApplication)

    alias(libs.plugins.composeHotReload)
}
//buildscript {
//    dependencies {
//        classpath(libs.plugins.sqldelight)
//    }
//}
//



kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm("desktop")





    // @OptIn(ExperimentalWasmDsl::class)

    js(IR) {
        moduleName = "composeApp"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }

        }
        binaries.executable()
    }
    sourceSets {
        val desktopMain by getting

        val jsMain by getting

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation("app.cash.sqldelight:android-driver:2.1.0")


            implementation(libs.koin.android)
            implementation(libs.koin.compose)

        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            // SQLDelight
            // implementation("com.squareup.sqldelight:runtime:1.3.0")
            //   implementation("com.squareup.sqldelight:android-driver:1.5.5")
            //  implementation ("com.squareup.sqldelight:sqlite-driver:1.5.5")

            implementation("app.cash.sqldelight:runtime:2.1.0")

            //  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")


            implementation("app.cash.sqldelight:coroutines-extensions:2.0.2")



            // implementation("app.cash.sqldelight:2.1.0")

            implementation(libs.koin.compose.viewmodel)

            implementation("co.touchlab:stately-common:2.1.0")

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)

            implementation("app.cash.sqldelight:runtime:2.1.0")
            implementation("app.cash.sqldelight:sqlite-driver:2.1.0")

            // facultatif : explicitement ajouter la stdlib jdk
            implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        }


//        val jsMain by getting {
//            dependencies {
//                // le runtime (hérité de commonMain, mais on peut le redéclarer si besoin)
//
//               // implementation("app.cash.sqldelight:runtime:2.0.2")
//
//           implementation("app.cash.sqldelight:web-worker-driver:2.0.2")  // :contentReference[oaicite:1]{index=1}
//
//               // implementation("app.cash.sqldelight:sqljs-driver:2.0.0")
//
//            }
//        }





        jsMain.dependencies{
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.7.3")
               // implementation("app.cash.sqldelight:sqljs-driver:2.0.0-alpha05")
                implementation("app.cash.sqldelight:web-worker-driver:2.1.0")

            implementation(compose.web.core)         // cœur de Compose Web
            implementation(compose.web.svg)          // si vous utilisez du SVG

            implementation("app.cash.sqldelight:sqljs-driver:2.0.0-alpha05")
            implementation(npm("sql.js", "1.8.0"))

          //  implementation("app.cash.sqldelight:sqljs-driver:2.0.2")
           // implementation("io.insert-koin:koin-js:3.4.0")  // Koin pour JS

//            implementation(npm("@cashapp/sqldelight-sqljs-worker", "2.1.0"))
//            implementation(npm("sql.js", "1.6.2"))
//            devNpm("copy-webpack-plugin", "9.1.0")
//
//
//            implementation(npm("path-browserify", "1.0.1"))
//            implementation(npm("crypto-browserify", "3.12.1"))
//            // si besoin, pour fournir `process` :
//            implementation(npm("process", "0.11.10"))
        }


        iosMain.dependencies {

            implementation("app.cash.sqldelight:runtime:2.1.0")
            implementation("app.cash.sqldelight:native-driver:2.1.0")


        }
    }
}



android {
    namespace = "org.babe.sqldelight"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.babe.sqldelight"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = VERSION_22
        targetCompatibility = VERSION_22
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "org.babe.sqldelight.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.babe.sqldelight"
            packageVersion = "1.0.0"
        }
    }
}
tasks.withType<ComposeHotRun>().configureEach {
    // replacez par votre fully-qualified name
    mainClass.set("org.babe.sqldelight.MainKt")
}


sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("org.babe.sqldelight.data.db")


        }

    }

}