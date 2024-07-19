import com.denisyordanp.truckticketapp.AppConfig
import com.denisyordanp.truckticketapp.SonarConfig
import com.denisyordanp.truckticketapp.TruckTicketModule
import com.denisyordanp.truckticketapp.implementModule

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.android.hilt)
    alias(libs.plugins.google.service)
    id("kotlin-kapt")
}

android {
    namespace = AppConfig.Android.NAMESPACE
    compileSdk = AppConfig.Android.COMPILE_SDK

    defaultConfig {
        applicationId = AppConfig.Android.NAMESPACE
        minSdk = AppConfig.Android.MIN_SDK
        targetSdk = AppConfig.Android.TARGET_SDK
        versionCode = AppConfig.Android.VERSION_CODE
        versionName = AppConfig.Android.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = AppConfig.Android.COMPATIBILITY_VERSION
        targetCompatibility = AppConfig.Android.COMPATIBILITY_VERSION
    }
    kotlinOptions {
        jvmTarget = AppConfig.Android.JVM_TARGET_VERSION
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = AppConfig.Android.COMPOSE_COMPILER_VERSION
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementModule(TruckTicketModule.SCHEMA)
    implementModule(TruckTicketModule.DOMAIN)
    implementModule(TruckTicketModule.COMMON)

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlin.coroutine)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.hilt.navigation)
    implementation(libs.androidx.accompanist.systemui)
    implementation(libs.androidx.accompanist.swiperefresh)

    implementation(libs.dagger.hilt)
    kapt(libs.dagger.compiler)

    implementation(platform(libs.google.firebase.bom))
    implementation(libs.google.firebase.analytics)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    testImplementation(libs.junit)
}

apply {
    from("${project.rootDir.path}/jacoco.gradle")
}

sonar {
    properties {
        property(SonarConfig.BRANCH_NAME, TruckTicketModule.APP.moduleName)
        property(SonarConfig.LINT_REPORT_PATH, AppConfig.Report.getLintReportPath(project.buildDir))
        property(SonarConfig.COVERAGE_EXCLUSIONS, AppConfig.Report.APP_COVERAGE_EXCLUSION)
    }
}