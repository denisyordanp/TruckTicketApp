import com.denisyordanp.truckticketapp.AppConfig
import com.denisyordanp.truckticketapp.SonarConfig
import com.denisyordanp.truckticketapp.TruckTicketModule

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
        property(SonarConfig.BRANCH_NAME, TruckTicketModule.COMMON.moduleName)
        property(
            SonarConfig.COVERAGE_JACOCO_XML_PATH,
            AppConfig.Report.getJacocoTestReportPath(project.buildDir)
        )
        property(SonarConfig.LINT_REPORT_PATH, AppConfig.Report.getLintReportPath(project.buildDir))
        property(SonarConfig.SOURCES_PATH, AppConfig.Source.MAIN_PATH)
        property(SonarConfig.TESTS_PATH, AppConfig.Source.TESTS_PATH)
    }
}