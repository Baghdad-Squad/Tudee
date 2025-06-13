package com.baghdad.tudee.textStyle

import androidx.compose.ui.text.TextStyle

data class TudeeTextStyle(
    val headline: SizedTextStyle,
    val title: SizedTextStyle,
    val body: SizedTextStyle,
    val label: SizedTextStyle,
){

    data class SizedTextStyle(
        val large: TextStyle,
        val medium: TextStyle,
        val small: TextStyle
    )

}
