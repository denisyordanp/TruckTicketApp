package com.denisyordanp.truckticketapp.common.extension

fun Any?.anyToLong() = tryCatchWithDefault(0) { this as Long? }.orZero()

fun Any?.toString() = tryCatchWithDefault("") { this as String? }.orEmpty()