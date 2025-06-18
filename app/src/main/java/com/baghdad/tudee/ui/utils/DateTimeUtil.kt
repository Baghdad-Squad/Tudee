package com.baghdad.tudee.ui.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char

fun LocalDate.formatDate(): String {
    val format = LocalDate.Format {
        dayOfMonth(padding = Padding.NONE)
        char('-')
        monthNumber(padding = Padding.NONE)
        char('-')
        year(padding = Padding.NONE)
    }
    return this.format(format)

}