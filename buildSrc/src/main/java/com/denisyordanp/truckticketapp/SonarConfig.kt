package com.denisyordanp.truckticketapp

import java.io.File

object SonarConfig {
    const val SONAR_BRANCH = "sonar.branch.name"
    const val SONAR_COVERAGE_XML_REPORT = "sonar.coverage.jacoco.xmlReportPaths"

    fun getJacocoTestReportPath(file: File) = "${file}/reports/jacoco/jacocoTestReport/jacocoTestReport.xml"
}