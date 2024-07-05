package com.denisyordanp.truckticketapp.common.extension

fun String?.orDefault(default: String) = this ?: default

fun String.safeToLong(default: Long = 0) = tryCatchWithDefault(default) {
    this.toLong()
}