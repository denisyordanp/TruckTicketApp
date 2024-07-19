plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.ksp) apply false
    alias(libs.plugins.android.hilt) apply false
    alias(libs.plugins.google.service) apply false
    alias(libs.plugins.sonarqube) apply true
}

sonar {
    properties {
        property("sonar.projectKey", "denisyordanp_TruckTicketApp")
        property("sonar.organization", "denisyordanp")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.exclusions", "**/test_util/**")
    }
}

val unitTestCoverageExclusions = mapOf(
    "app" to "**/ui/**, **/util/**, **/app/**",
    "core" to "**/database/**, **/remote/**",
)
val sonarModuleExclusion = arrayOf("test_util")

subprojects {
    sonar {
        properties {
            if (project.name !in sonarModuleExclusion) {
                val currentBuildDir = layout.buildDirectory.asFile.get().path

                property("sonar.branch.name", project.name)
                property(
                    "sonar.coverage.jacoco.xmlReportPaths",
                    "$currentBuildDir/reports/jacoco/jacocoTestReport/jacocoTestReport.xml"
                )
                property(
                    "sonar.androidLint.reportPaths",
                    "$currentBuildDir/reports/lint-results-debug.xml"
                )
                unitTestCoverageExclusions[project.name]?.let {
                    property("sonar.coverage.exclusions", it)
                }
            }
        }
    }
}