package com.baghdad.tudee.ui.utils

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun StatusBarTitleTheme(isDark: Boolean) {
    val view = LocalView.current
    val window = (view.context as Activity).window

    SideEffect {
        WindowInsetsControllerCompat(window!!, view).isAppearanceLightStatusBars = !isDark
    }
}