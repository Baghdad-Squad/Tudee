package com.baghdad.tudee.designSystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.baghdad.tudee.designSystem.color.TudeeColor
import com.baghdad.tudee.designSystem.color.localTudeeColor
import com.baghdad.tudee.designSystem.textStyle.TudeeTextStyle
import com.baghdad.tudee.designSystem.textStyle.localTudeeTextStyle

object Theme {
    val color: TudeeColor
        @Composable @ReadOnlyComposable get() = localTudeeColor.current
    val typography: TudeeTextStyle
        @Composable @ReadOnlyComposable get() = localTudeeTextStyle.current

}