package com.denisyordanp.truckticketapp.common.extension

import com.denisyordanp.truckticketapp.common.util.DateFormat
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.util.Calendar

class LongTest {
    @Test
    fun `orZero returns zero when input is null`() {
        val input: Long? = null
        val result = input.orZero()
        assertEquals(0L, result)
    }

    @Test
    fun `orZero returns original value when input is not null`() {
        val input = 123L
        val result = input.orZero()
        assertEquals(123L, result)
    }

    @Test
    fun `dateFormatToHour returns correct hour`() {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 10)
            set(Calendar.MINUTE, 30)
        }
        val input = calendar.timeInMillis
        val result = input.dateFormatToHour()
        assertEquals(10, result)
    }

    @Test
    fun `dateFormatToMinute returns correct minute`() {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 10)
            set(Calendar.MINUTE, 30)
        }
        val input = calendar.timeInMillis
        val result = input.dateFormatToMinute()
        assertEquals(30, result)
    }

    @Test
    fun `toFormattedDateString returns correct formatted string`() {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, 2020)
            set(Calendar.MONTH, Calendar.JANUARY)
            set(Calendar.DAY_OF_MONTH, 1)
        }
        val input = calendar.timeInMillis
        val result = input.toFormattedDateString(DateFormat.DAY_MONTH_YEAR)
        assertEquals("01/01/2020", result)
    }

    @Test
    fun `isZero returns true when value is zero`() {
        val input = 0L
        val result = input.isZero()
        assertEquals(true, result)
    }

    @Test
    fun `isZero returns false when value is not zero`() {
        val input = 123L
        val result = input.isZero()
        assertEquals(false, result)
    }
}