package com.baghdad.tudee.designSystem.color


import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

data class TudeeColor(
    val primaryColor: Primary,
    val secondaryColor: Color,
    val textColor: TextColor,
    val surfaceColor: SurfaceColor,
    val status: StatusColor,
)

data class Primary(
    val normal: Color,
    val variant: Color,
    val gradient: Brush
)

data class TextColor(
    val title: Color,
    val body: Color,
    val hint: Color,
    val stroke: Color,

    val onPrimary: Color,
    val onPrimaryCaption: Color,
    val onPrimaryCard: Color,
    val onPrimaryStroke: Color,
    val disable: Color
)

data class SurfaceColor(
    val surfaceLow: Color,
    val surface: Color,
    val surfaceHigh: Color,
    val ellipsisGradientColor : Brush,
)

data class StatusColor(
    val pinkAccent:Color,
    val yellowAccent:Color,
    val greenAccent:Color,
    val purpleAccent:Color,
    val error:Color,
    val overlay:Color,
    val emojiTint: Color,
    val yellowVariant : Color,
    val greenVariant : Color,
    val purpleVariant : Color,
    val errorVariant : Color
)


internal val  localTudeeColor= staticCompositionLocalOf {
    lightThemeColor
}




