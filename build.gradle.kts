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