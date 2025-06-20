package com.baghdad.tudee.ui.screens.SplashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.ui.screens.OnboardingScreen.OnboardingBackground

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier
) {
    OnboardingBackground {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Tudee Logo",
                modifier = Modifier
                    .width(122.dp)
                    .height(48.dp)
                    .border(
                        width = 1.dp,
                        color = Theme.color.textColor.stroke,
                    )
            )
        }
    }
}