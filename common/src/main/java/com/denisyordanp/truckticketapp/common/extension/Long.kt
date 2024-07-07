package com.denisyordanp.truckticketapp.common.extension

import com.denisyordanp.truckticketapp.common.util.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun Long?.orZero() = this ?: 0

fun Long.dateFormatToHour(locale: Locale = Locale.getDefault()): Int {
    val date = Calendar.getInstance(locale)
    date.timeInMillis = this

    return tryCatchWithDefault(0) {
        date[Calendar.HOUR_OF_DAY]
    }
}

fun Long.dateFormatToMinute(locale: Locale = Locale.getDefault()): Int {
    val date = Calendar.getInstance(locale)
    date.timeInMillis = this

    return tryCatchWithDefault(0) {
        date[Calendar.MINUTE]
    }
}

fun Long.toFormattedDateString(format: DateFormat, locale: Locale = Locale.getDefault()): String {
    val dateFormat = SimpleDateFormat(format.pattern, locale)
    val date = Calendar.getInstance(locale)
    date.timeInMillis = this

    return tryCatchWithDefault("") {
        dateFormat.format(date.time)
    }
}

fun Long.isZero() = this == 0L