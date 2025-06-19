package com.baghdad.tudee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.baghdad.tudee.designSystem.theme.TudeeTheme
import com.baghdad.tudee.ui.component.OnboardingScreen
import com.baghdad.tudee.ui.component.SplashScreen
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TudeeTheme {

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

            }
        }
    }
}
