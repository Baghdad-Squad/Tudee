package com.baghdad.tudee.designSystem.color


import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


val darkThemeColor = TudeeColor(
    primaryColor = Primary(
        normal = Color(0xFF3090BF),
        variant = Color(0xFF05202E),
        gradient = Brush.linearGradient(
            colors = listOf(
                Color(0xFF3DB6F2),
                Color(0xFF3A9CCD)
            )
        )
    ),
    secondaryColor = Color(0xFFF49061),

    textColor = TextColor(
        title = Color(0xDEFFFFFF),
        body = Color(0x99FFFFFF),
        hint = Color(0x61FFFFFF),
        stroke = Color(0x1FFFFFFF),

        onPrimary = Color(0xDEFFFFFF),
        onPrimaryCaption = Color(0xB3FFFFFF),
        onPrimaryCard = Color(0x29060414),
        onPrimaryStroke = Color(0x99242424),
        disable = Color(0xFF1D1E1F),
    ),
    surfaceColor = SurfaceColor(
        surfaceLow = Color(0xFF020108),
        surface = Color(0xFF0D0C14),
        surfaceHigh = Color(0xFF0F0E19),
        ellipsisGradientColor = Brush.linearGradient(
            colors = listOf(
                Color(0x8005202E),
                Color(0x0005202E),

                )
        ),
    ),
    status = StatusColor(
        pinkAccent = Color(0xFFCC5268), //#CC5268
        yellowAccent = Color(0xFFB28F25),//##B28F25
        greenAccent = Color(0xFF4D8064), //#4D8064
        purpleAccent = Color(0xFF6F63B2), // #6F63B2
        error = Color(0xFFE55C5C),
        overlay = Color(0x5202151E),
        emojiTint = Color(0xDE1F1F1F),
        yellowVariant = Color(0xFFF7F2E4) ,
        greenVariant = Color(0xFFE4F2EA),
        purpleVariant = Color(0xFFEEEDF7),
        errorVariant = Color(0xFFFCE8E8)
    ),
)











