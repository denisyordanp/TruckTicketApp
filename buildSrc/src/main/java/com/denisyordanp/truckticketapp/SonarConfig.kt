package com.denisyordanp.truckticketapp

import java.io.File

object SonarConfig {
    const val BRANCH_NAME = "sonar.branch.name"
    const val COVERAGE_JACOCO_XML_PATH = "sonar.coverage.jacoco.xmlReportPaths"
    const val SOURCES_PATH = "sonar.sources"
    const val TESTS_PATH = "sonar.tests"
    const val LINT_REPORT_PATH = "sonar.androidLint.reportPaths"
    const val COVERAGE_EXCLUSIONS = "sonar.coverage.exclusions"
}