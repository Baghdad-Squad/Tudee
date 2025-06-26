package com.baghdad.tudee.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.designSystem.theme.TudeeTheme
import com.baghdad.tudee.ui.composable.BottomNavigation
import com.baghdad.tudee.ui.navigation.LocalNavController
import com.baghdad.tudee.ui.navigation.Route
import com.baghdad.tudee.ui.navigation.TudeeNavHost
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainViewModel = koinViewModel<MainViewModel>()
            val state by mainViewModel.uiState.collectAsStateWithLifecycle()
            val navController = rememberNavController()
            CompositionLocalProvider(
                LocalNavController provides navController
            ) {
                TudeeTheme(
                    isDarkTheme = state.isDarkTheme == true,
                ) {
                    AnimatedContent(
                        targetState = state.isDarkTheme == null || state.isFirstLaunch == null
                    ) { isLoading ->
                        if (!isLoading) {
                            AppContent(
                                isFirstLaunch = state.isFirstLaunch != false,
                                navController = navController
                            )
                        }

                    }
                }
            }
        }

    }

    @Composable
    fun AppContent(
        isFirstLaunch: Boolean,
        navController: NavHostController
    ) {
        val startDestination = remember(isFirstLaunch) {
            if (isFirstLaunch) {
                Route.OnboardingScreen
            } else {
                Route.HomeScreen
            }
        }
        Column(
            modifier = Modifier
                .background(Theme.color.surfaceColor.surfaceHigh)
                .navigationBarsPadding()
                .fillMaxSize()
        ) {
            TudeeNavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier
                    .weight(1f)
            )
            BottomNavigation(navController = navController)
        }
    }
}