plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.salazar.lab02carritokotlin"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.salazar.lab02carritokotlin"
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
tasks.register<JavaExec>("ejecutarCarrito") {
    val compileTask = tasks.named<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>("compileDebugKotlin")
    dependsOn(compileTask)
    mainClass.set("com.salazar.lab02carritokotlin.CarritoKt")
    classpath = files(
        compileTask.get().destinationDirectory,
        configurations.getByName("debugRuntimeClasspath")
    )
}

tasks.register<JavaExec>("ejecutarCarritoIA") {
    dependsOn("compileDebugKotlin")
    mainClass.set("com.salazar.lab02carritokotlin.CarritoIAKt")
    classpath = files(
        tasks.named<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>("compileDebugKotlin").get().destinationDirectory,
        configurations.getByName("debugRuntimeClasspath")
    )
}