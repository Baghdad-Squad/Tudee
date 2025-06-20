package com.baghdad.tudee.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.baghdad.tudee.designSystem.theme.TudeeTheme
import com.baghdad.tudee.ui.screens.OnboardingScreen.OnboardingScreen
import com.baghdad.tudee.ui.screens.SplashScreen.SplashScreen
import kotlinx.coroutines.delay
import org.koin.android.ext.android.getKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val mainViewModel = getKoin().get<MainViewModel>()
        setContent {
                val state = mainViewModel.uiState.collectAsStateWithLifecycle()
                TudeeTheme(
                    isDarkTheme = state.value.isDarkTheme
                ) {

                    val currentTimeOut = remember { mutableStateOf(true) }

                    LaunchedEffect(Unit) {
                        delay(2000)
                        currentTimeOut.value = false
                    }
                    AnimatedVisibility(
                        currentTimeOut.value
                    ) {
                        SplashScreen()
                    }

                    AnimatedVisibility(
                        visible = !currentTimeOut.value,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        OnboardingScreen { }
                    }

                    //content
                }

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
