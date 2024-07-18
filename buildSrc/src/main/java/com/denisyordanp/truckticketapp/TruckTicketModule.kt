package com.denisyordanp.truckticketapp

import org.gradle.api.Project

enum class TruckTicketModule(val moduleName: String) {
    APP("app"),
    CORE("core"),
    DATA("data"),
    COMMON("common"),
    DOMAIN("domain"),
    SCHEMA("schema"),
    TEST_UTIL("test_util");

    fun forImplementation() = ":$moduleName"
}

fun Project.implementModule(module: TruckTicketModule) {
    dependencies.add("implementation", project(module.forImplementation()))
}

fun Project.testModuleImplement(module: TruckTicketModule) {
    dependencies.add("testImplementation", project(module.forImplementation()))
}