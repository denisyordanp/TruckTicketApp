import com.denisyordanp.truckticketapp.SonarConfig
import com.denisyordanp.truckticketapp.TruckTicketAndroidConfig
import com.denisyordanp.truckticketapp.TruckTicketModule

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
    testOptions.unitTests.isReturnDefaultValues = true
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlin.coroutine)

    implementation(libs.dagger.hilt)
    kapt(libs.dagger.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.coroutine.test)
}

apply {
    from("${project.rootDir.path}/jacoco.gradle")
}

sonar {
    properties {
        property(SonarConfig.SONAR_BRANCH, TruckTicketModule.COMMON.moduleName)
        property(SonarConfig.SONAR_COVERAGE_XML_REPORT, SonarConfig.getJacocoTestReportPath(project.buildDir))
    }
}