package com.denisyordanp.truckticketapp

import org.gradle.api.JavaVersion
import java.io.File

object AppConfig {
    object Android {
        const val NAMESPACE = "com.denisyordanp.truckticketapp"

        const val COMPILE_SDK = 34
        const val TARGET_SDK = 34
        const val MIN_SDK = 24
        const val VERSION_CODE = 1
        const val VERSION_NAME = "1.0"

        const val COMPOSE_COMPILER_VERSION = "1.4.4"

        const val JVM_TARGET_VERSION = "17"
        val COMPATIBILITY_VERSION = JavaVersion.VERSION_17

        fun createModuleNameSpace(path: String) = "${Android.NAMESPACE}.${path.removePrefix(":").replace(":", ".")}"
    }

    object Source {
        const val MAIN_PATH = "src/main"
        const val TESTS_PATH = "src/test"
    }

    object Report {
        const val CORE_COVERAGE_EXCLUSION = "**/database/**, **/remote/**"
        const val APP_COVERAGE_EXCLUSION = "**/ui/**, **/util/**"

        fun getJacocoTestReportPath(file: File) = "${file}/reports/jacoco/jacocoTestReport/jacocoTestReport.xml"
        fun getLintReportPath(file: File) = "${file}/reports/lint-results-debug.xml"
    }
}

