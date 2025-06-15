package com.baghdad.tudee.designSystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.baghdad.tudee.designSystem.color.darkThemeColor
import com.baghdad.tudee.designSystem.color.lightThemeColor
import com.baghdad.tudee.designSystem.color.localTudeeColor
import com.baghdad.tudee.designSystem.textStyle.localTudeeTextStyle
import com.baghdad.tudee.designSystem.textStyle.tudeeTextStyle


@Composable
fun TudeeTheme(
    isDarkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val theme = if (isDarkTheme) darkThemeColor else lightThemeColor

    CompositionLocalProvider(
        localTudeeColor provides theme,
        localTudeeTextStyle provides tudeeTextStyle
    )
     {
        content()
    }

}

