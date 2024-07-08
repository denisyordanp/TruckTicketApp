package com.denisyordanp.truckticketapp.common.extension

import android.util.Log

fun <T> tryCatchWithDefault(default: T, block: () -> T) = try {
    block()
} catch (e: Exception) {
    Log.d("tryCatchWithDefault", e.message.orEmpty())
    default
}

suspend fun <T> safeCallWrapper(
    call: suspend () -> T,
    onStart: (suspend () -> Unit)? = null,
    onFinish: (suspend (T) -> Unit)? = null,
    onError: (suspend (Exception) -> Unit)? = null,
) {
    onStart?.invoke()
    try {
        val result = call()
        onFinish?.invoke(result)
    } catch (e: Exception) {
        onError?.invoke(e)
    }
}