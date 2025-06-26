package com.baghdad.tudee.ui.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import java.text.NumberFormat
import java.util.Locale

fun getLocalizedNumber(number: Number): String {
    val formatter = NumberFormat.getInstance(Locale.getDefault())
    return formatter.format(number)
}

val arabicNumbers = mapOf(
    '0' to '٠', '1' to '١', '2' to '٢', '3' to '٣', '4' to '٤',
    '5' to '٥', '6' to '٦', '7' to '٧', '8' to '٨', '9' to '٩'
)

fun convertToArabicNumbers(input: Int): String {
    return input.toString().map { arabicNumbers[it] ?: it }.joinToString("")
}


fun formatDateLocalized(date: LocalDate): String {
    val locale = Locale.getDefault()

    val day = date.dayOfMonth
    val year = date.year
    val month = date.month

    val arabicNumbers = mapOf(
        '0' to '٠', '1' to '١', '2' to '٢', '3' to '٣', '4' to '٤',
        '5' to '٥', '6' to '٦', '7' to '٧', '8' to '٨', '9' to '٩'
    )

    fun convertToArabicNumbers(input: Int): String {
        return input.toString().map { arabicNumbers[it] ?: it }.joinToString("")
    }

    val monthName = when (locale.language) {
        "ar" -> when (month) {
            Month.JANUARY -> "يناير"
            Month.FEBRUARY -> "فبراير"
            Month.MARCH -> "مارس"
            Month.APRIL -> "أبريل"
            Month.MAY -> "مايو"
            Month.JUNE -> "يونيو"
            Month.JULY -> "يوليو"
            Month.AUGUST -> "أغسطس"
            Month.SEPTEMBER -> "سبتمبر"
            Month.OCTOBER -> "أكتوبر"
            Month.NOVEMBER -> "نوفمبر"
            Month.DECEMBER -> "ديسمبر"
        }
        else -> month.name.lowercase().replaceFirstChar { it.uppercase() }
    }

    return if (locale.language == "ar") {
        "${convertToArabicNumbers(day)} $monthName ${convertToArabicNumbers(year)}"
    } else {
        "$day $monthName $year"
    }
}