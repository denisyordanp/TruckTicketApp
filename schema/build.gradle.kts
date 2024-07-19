import com.denisyordanp.truckticketapp.AppConfig
import com.denisyordanp.truckticketapp.SonarConfig
import com.denisyordanp.truckticketapp.TruckTicketModule
import com.denisyordanp.truckticketapp.implementModule
import com.denisyordanp.truckticketapp.testModuleImplement

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
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
    implementModule(TruckTicketModule.COMMON)

    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.room.runtime)

    implementation(platform(libs.google.firebase.bom))
    implementation(libs.google.firebase.firestore)

    testModuleImplement(TruckTicketModule.TEST_UTIL)
    testImplementation(libs.junit)
}

apply {
    from("${project.rootDir.path}/jacoco.gradle")
}

sonar {
    properties {
        property(SonarConfig.BRANCH_NAME, TruckTicketModule.SCHEMA.moduleName)
        property(
            SonarConfig.COVERAGE_JACOCO_XML_PATH,
            AppConfig.Report.getJacocoTestReportPath(project.buildDir)
        )
        property(SonarConfig.LINT_REPORT_PATH, AppConfig.Report.getLintReportPath(project.buildDir))
        property(SonarConfig.SOURCES_PATH, AppConfig.Source.MAIN_PATH)
        property(SonarConfig.TESTS_PATH, AppConfig.Source.TESTS_PATH)
    }
}