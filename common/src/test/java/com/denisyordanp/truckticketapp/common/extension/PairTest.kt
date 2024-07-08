package com.denisyordanp.truckticketapp.common.extension

import junit.framework.TestCase.assertEquals
import org.junit.Test

class PairTest {

    @Test
    fun `pairOf creates a pair with correct values`() {
        val first = 1
        val second = "a"
        val result = pairOf(first, second)

        assertEquals(Pair(first, second), result)
    }

    @Test
    fun `pairOf creates a pair with null values`() {
        val first: Int? = null
        val second: String? = null
        val result = pairOf(first, second)

        assertEquals(Pair(first, second), result)
    }

    @Test
    fun `pairOf creates a pair with different types`() {
        val first = 1
        val second = 2.0
        val result = pairOf(first, second)

        assertEquals(Pair(first, second), result)
    }

    @Test
    fun `pairOf creates a pair with complex types`() {
        val first = listOf(1, 2, 3)
        val second = mapOf("a" to 1)
        val result = pairOf(first, second)

        assertEquals(Pair(first, second), result)
    }
}