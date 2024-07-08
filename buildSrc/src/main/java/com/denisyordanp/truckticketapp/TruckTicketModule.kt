package com.denisyordanp.truckticketapp

import org.gradle.api.Project

enum class TruckTicketModule(val moduleName: String) {
    CORE(":core"),
    DATA(":data"),
    COMMON(":common"),
    DOMAIN(":domain"),
    SCHEMA(":schema"),
    TEST_UTIL(":test_util")
}

fun Project.implementModule(module: TruckTicketModule) {
    dependencies.add("implementation", project(module.moduleName))
}

fun Project.testModuleImplement(module: TruckTicketModule) {
    dependencies.add("testImplementation", project(module.moduleName))
}