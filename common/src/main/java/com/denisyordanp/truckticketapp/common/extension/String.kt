package com.denisyordanp.truckticketapp.common.extension

import java.util.Locale

fun String?.orDefault(default: String) = this ?: default

fun String.safeToLong(default: Long = 0): Long {
    val reformatted = this.replace(Regex("[^0-9]"), "")

    return tryCatchWithDefault(default) {
        reformatted.toLong()
    }
}

fun String.onlyAlphanumeric() = this.replace(Regex("[^A-Za-z0-9]"), "")

fun String.capitalizeFirstChar(locale: Locale = Locale.getDefault()) = this.split(" ")
    .joinToString(" ") { it.capitalize(locale) }