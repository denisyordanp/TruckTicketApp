package com.denisyordanp.truckticketapp.common.extension

import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.util.Locale

class StringTest {
    @Test
    fun `orDefault returns original string when not null`() {
        val original = "Hello"
        val default = "Default"
        val result = original.orDefault(default)
        assertEquals(original, result)
    }

    @Test
    fun `orDefault returns default string when original is null`() {
        val original: String? = null
        val default = "Default"
        val result = original.orDefault(default)
        assertEquals(default, result)
    }

    @Test
    fun `acceptDigitOnly returns only digits from string`() {
        val input = "abc123def"
        val expected = 123L
        val result = input.acceptDigitOnly()
        assertEquals(expected, result)
    }

    @Test
    fun `acceptDigitOnly returns default value when no digits are present`() {
        val input = "abc"
        val default = 0L
        val result = input.acceptDigitOnly(default)
        assertEquals(default, result)
    }

    @Test
    fun `onlyAlphanumeric removes non-alphanumeric characters`() {
        val input = "abc!@#123"
        val expected = "abc123"
        val result = input.onlyAlphanumeric()
        assertEquals(expected, result)
    }

    @Test
    fun `capitalizeFirstChar capitalizes first character of each word`() {
        val input = "hello world"
        val expected = "Hello World"
        val result = input.capitalizeFirstChar()
        assertEquals(expected, result)
    }

    @Test
    fun `capitalizeFirstChar capitalizes correctly with custom locale`() {
        val input = "hello world"
        val expected = "Hello World"
        val locale = Locale("tr", "TR") // Turkish locale as an example
        val result = input.capitalizeFirstChar(locale)
        assertEquals(expected, result)
    }
}