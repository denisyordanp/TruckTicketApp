package com.denisyordanp.truckticketapp

import org.gradle.api.Project

enum class TruckTicketModule(val moduleName: String) {
    CORE(":core"), DATA(":data"), COMMON(":common"), DOMAIN(":domain"), SCHEMA(":schema")
}

fun Project.implementModule(module: TruckTicketModule) {
    dependencies.add("implementation", project(module.moduleName))
}