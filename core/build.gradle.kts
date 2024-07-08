import com.denisyordanp.truckticketapp.TruckTicketAndroidConfig
import com.denisyordanp.truckticketapp.TruckTicketModule
import com.denisyordanp.truckticketapp.implementModule

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.android.hilt)
    id("kotlin-kapt")
}

apply {
    from("${project.rootDir.path}/jacoco.gradle")
}

android {
    namespace = TruckTicketAndroidConfig.createModuleNameSpace(project.path)
    compileSdk = TruckTicketAndroidConfig.COMPILE_SDK

    defaultConfig {
        minSdk = TruckTicketAndroidConfig.MIN_SDK
    }

    compileOptions {
        sourceCompatibility = TruckTicketAndroidConfig.COMPATIBILITY_VERSION
        targetCompatibility = TruckTicketAndroidConfig.COMPATIBILITY_VERSION
    }
    kotlinOptions {
        jvmTarget = TruckTicketAndroidConfig.JVM_TARGET_VERSION
    }
}

dependencies {
    implementModule(TruckTicketModule.SCHEMA)
    implementModule(TruckTicketModule.COMMON)

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlin.coroutine)

    implementation(libs.dagger.hilt)
    kapt(libs.dagger.compiler)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    implementation(platform(libs.google.firebase.bom))
    implementation(libs.google.firebase.firestore)
}