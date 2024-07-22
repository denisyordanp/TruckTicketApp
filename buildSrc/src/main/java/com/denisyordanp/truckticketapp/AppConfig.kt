package com.denisyordanp.truckticketapp

import org.gradle.api.JavaVersion

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

        fun createModuleNameSpace(path: String) =
            "$NAMESPACE.${path.removePrefix(":").replace(":", ".")}"
    }
}

