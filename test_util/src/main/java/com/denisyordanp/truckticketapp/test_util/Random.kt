package com.denisyordanp.truckticketapp.test_util

import kotlin.random.Random

fun Random.nextString(length: Int = 5): String {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    val result = StringBuilder(length)
    for (i in 0 until length) {
        val charIndex = this.nextInt(chars.length)
        result.append(chars[charIndex])
    }
    return result.toString()
}