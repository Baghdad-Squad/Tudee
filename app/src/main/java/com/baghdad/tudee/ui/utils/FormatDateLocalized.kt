package com.baghdad.tudee.ui.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import java.util.Locale
fun getLocalizedDateParts(date: LocalDate): Triple<String, String, String> {
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

    val dayStr = if (locale.language == "ar") convertToArabicNumbers(day) else day.toString()
    val yearStr = if (locale.language == "ar") convertToArabicNumbers(year) else year.toString()

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

    return Triple(dayStr, monthName, yearStr)
}

fun formatDateLocalized(date: LocalDate): String {
    val (dayStr, monthName, yearStr) = getLocalizedDateParts(date)
    return "$dayStr $monthName $yearStr"
}
