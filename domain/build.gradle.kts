import com.denisyordanp.truckticketapp.SonarConfig
import com.denisyordanp.truckticketapp.TruckTicketAndroidConfig
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

    testModuleImplement(TruckTicketModule.TEST_UTIL)
    testImplementation(libs.junit)
    testImplementation(libs.kotlin.coroutine.test)
    testImplementation(libs.mockito.kotlin)
}

apply {
    from("${project.rootDir.path}/jacoco.gradle")
}

sonar {
    properties {
        property(SonarConfig.SONAR_BRANCH, TruckTicketModule.DOMAIN.moduleName)
        property(SonarConfig.SONAR_COVERAGE_XML_REPORT, SonarConfig.getJacocoTestReportPath(project.buildDir))
    }
}