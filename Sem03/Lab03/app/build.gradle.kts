plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.salazar.lab03"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.salazar.lab03"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}

tasks.register<JavaExec>("ejecutarCuotas") {
    val compileTask = tasks.named<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>("compileDebugKotlin")
    dependsOn(compileTask)
    mainClass.set("com.salazar.lab03.MainKt")
    classpath = files(
        compileTask.get().destinationDirectory,
        configurations.getByName("debugRuntimeClasspath")
    )
    standardInput = System.`in`
}