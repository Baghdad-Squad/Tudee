package com.baghdad.tudee.ui.main

import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.baghdad.tudee.designSystem.theme.TudeeTheme
import com.baghdad.tudee.ui.navigation.LocalNavController
import com.baghdad.tudee.ui.screens.SplashScreen.SplashScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = koinViewModel<MainViewModel>()
) {
    val state by mainViewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        TudeeTheme(isDarkTheme = state.isDarkTheme == true) {
            val isLoading = state.isDarkTheme == null || state.isFirstLaunch == null
            AnimatedContent(
                targetState = isLoading,
                label = "isLoadingToAppTransition"
            ) { isLoadingState ->
                if (isLoadingState) {
                    SplashScreen()
                } else {
                    AppContent(
                        isFirstLaunch = state.isFirstLaunch != false, navController = navController
                    )
                }
            }
        }
    }
}