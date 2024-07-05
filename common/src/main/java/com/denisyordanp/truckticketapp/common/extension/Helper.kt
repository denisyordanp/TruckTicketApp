package com.denisyordanp.truckticketapp.common.extension

import android.util.Log

fun <T> tryCatchWithDefault(default: T, block: () -> T) = try {
    block()
} catch (e: Exception) {
    Log.d("tryCatchWithDefault", e.message.orEmpty())
    default
}