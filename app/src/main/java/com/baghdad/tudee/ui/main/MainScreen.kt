package com.baghdad.tudee.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.baghdad.tudee.designSystem.theme.TudeeTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = koinViewModel<MainViewModel>()
) {
    val state by mainViewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()

    TudeeTheme(isDarkTheme = state.isDarkTheme == true) {
        val isLoading = state.isDarkTheme == null || state.isFirstLaunch == null
        AnimatedVisibility(
            visible = !isLoading,
            label = "isLoadingToAppTransition"
        ) {
            AppContent(
                isFirstLaunch = state.isFirstLaunch != false, navController = navController
            )
        }
    }
}
