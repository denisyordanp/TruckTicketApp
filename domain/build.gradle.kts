import com.denisyordanp.truckticketapp.AppConfig
import com.denisyordanp.truckticketapp.TruckTicketModule
import com.denisyordanp.truckticketapp.implementModule
import com.denisyordanp.truckticketapp.testModuleImplement

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.android.hilt)
    id("kotlin-kapt")
}

android {
    namespace = AppConfig.Android.createModuleNameSpace(project.path)
    compileSdk = AppConfig.Android.COMPILE_SDK

    defaultConfig {
        minSdk = AppConfig.Android.MIN_SDK
    }

    compileOptions {
        sourceCompatibility = AppConfig.Android.COMPATIBILITY_VERSION
        targetCompatibility = AppConfig.Android.COMPATIBILITY_VERSION
    }
    kotlinOptions {
        jvmTarget = AppConfig.Android.JVM_TARGET_VERSION
    }
}

dependencies {
    implementModule(TruckTicketModule.SCHEMA)
    implementModule(TruckTicketModule.DATA)
    implementModule(TruckTicketModule.COMMON)

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlin.coroutine)

    implementation(libs.dagger.hilt)
    kapt(libs.dagger.compiler)

    testModuleImplement(TruckTicketModule.TEST_UTIL)
    testImplementation(libs.junit)
    testImplementation(libs.kotlin.coroutine.test)
    testImplementation(libs.mockito.kotlin)
}

apply {
    from("${project.rootDir.path}/jacoco.gradle")
}