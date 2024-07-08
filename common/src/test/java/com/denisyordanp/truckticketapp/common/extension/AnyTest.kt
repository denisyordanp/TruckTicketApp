package com.denisyordanp.truckticketapp.common.extension

import junit.framework.TestCase.assertEquals
import org.junit.Test

class AnyTest {
    @Test
    fun `anyToLong returns long value when input is Long`() {
        val input: Any = 123L
        val result = input.anyToLong()
        assertEquals(123L, result)
    }

    @Test
    fun `anyToLong returns zero when input is not Long`() {
        val input: Any = "123"
        val result = input.anyToLong()
        assertEquals(0L, result)
    }

    @Test
    fun `anyToLong returns zero when input is null`() {
        val input: Any? = null
        val result = input.anyToLong()
        assertEquals(0L, result)
    }

    @Test
    fun `anyToString returns string value when input is String`() {
        val input: Any = "hello"
        val result = input.anyToString()
        assertEquals("hello", result)
    }

    @Test
    fun `anyToString returns empty string when input is not String`() {
        val input: Any = 123
        val result = input.anyToString()
        assertEquals("", result)
    }

    @Test
    fun `anyToString returns empty string when input is null`() {
        val input: Any? = null
        val result = input.anyToString()
        assertEquals("", result)
    }
}