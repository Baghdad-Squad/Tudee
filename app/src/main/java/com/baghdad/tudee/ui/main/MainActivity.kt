package com.baghdad.tudee.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.baghdad.tudee.designSystem.theme.TudeeTheme
import com.baghdad.tudee.ui.composable.BottomNavigation
import com.baghdad.tudee.ui.navigation.Route
import com.baghdad.tudee.ui.navigation.TudeeNavHost
import com.baghdad.tudee.ui.screens.SplashScreen.SplashScreen
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainViewModel = koinViewModel<MainViewModel>()
            val state by mainViewModel.uiState.collectAsStateWithLifecycle()
            TudeeTheme(
                isDarkTheme = state.isDarkTheme == true
            ) {
                AnimatedContent(
                    targetState = state.isDarkTheme == null || state.isFirstLaunch == null
                ) { isLoading ->
                    if (isLoading) {
                        SplashScreen()
                    } else {
                        AppContent(
                            isFirstLaunch = state.isFirstLaunch != false,
                        )
                    }

                }
            }
        }

    }

    @Composable
    fun AppContent(
        isFirstLaunch: Boolean,
    ) {
        val navController = rememberNavController()
        val startDestination = remember(isFirstLaunch) {
            if (isFirstLaunch) {
                Route.OnboardingScreen
            } else {
                Route.HomeScreen
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TudeeTheme {
        Greeting("Android")
    }
}
