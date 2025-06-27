package com.baghdad.tudee.ui.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.format
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DecimalStyle
import java.time.format.TextStyle
import java.util.Locale

fun LocalDate.formatDate(pattern: String = "dd MMM yyyy"): String {
    val javaDate = this.toJavaLocalDate()
    val locale = Locale.getDefault()
    val decimalStyle = DecimalStyle.of(locale)
    val formatter = DateTimeFormatter.ofPattern(pattern, locale)
        .withDecimalStyle(decimalStyle)
    return javaDate.format(formatter)
}


fun Month.toLocalizedStringModern(
    locale: Locale = Locale.getDefault(),
    textStyle: TextStyle = TextStyle.SHORT
): String {
    val javaMonth = java.time.Month.of(this.ordinal + 1)
    return javaMonth.getDisplayName(textStyle, locale)
}