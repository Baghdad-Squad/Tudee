package com.baghdad.tudee.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.baghdad.tudee.presentation.navigation.Route

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