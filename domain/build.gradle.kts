import com.denisyordanp.truckticketapp.TruckTicketAndroidConfig
import com.denisyordanp.truckticketapp.TruckTicketModule
import com.denisyordanp.truckticketapp.implementModule

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.android.hilt)
    id("kotlin-kapt")
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
    implementModule(TruckTicketModule.DATA)
    implementModule(TruckTicketModule.COMMON)

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlin.coroutine)

    implementation(libs.dagger.hilt)
    kapt(libs.dagger.compiler)

    testImplementation(libs.junit)
}