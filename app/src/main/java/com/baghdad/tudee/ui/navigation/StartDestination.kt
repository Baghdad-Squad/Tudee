package com.baghdad.tudee.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberStartDestination(
    isFirstLaunch: Boolean
): Route {
    return remember(isFirstLaunch) {
        if (isFirstLaunch) {
            Route.OnboardingScreen
        } else {
            Route.HomeScreen
        }
    }
}