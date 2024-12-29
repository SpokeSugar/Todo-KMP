import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)

    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidTarget {
        publishLibraryVariants("release")

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }

        dependencies {
            testImplementation(libs.kotlin.test)
            testImplementation(libs.androidx.paging.common)
            testImplementation(libs.androidx.test.junit)
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()

    applyDefaultHierarchyTemplate()

    sourceSets {

        commonMain.dependencies {
            api(libs.androidx.paging.common)
            implementation(compose.foundation)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        jvmTest.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
            implementation(compose.uiTooling)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
            implementation(compose.desktop.currentOs)
        }

        val nonAndroidMain by creating { dependsOn(commonMain.get()) }

        iosMain.get().dependsOn(nonAndroidMain)
        jvmMain.get().dependsOn(nonAndroidMain)
    }
}

android {
    namespace = "androidx.paging.compose"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}