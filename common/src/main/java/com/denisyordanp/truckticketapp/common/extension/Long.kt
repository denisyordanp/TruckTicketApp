package com.denisyordanp.truckticketapp.common.extension

object LongExtension {
    fun Long?.orZero() = this ?: 0
}