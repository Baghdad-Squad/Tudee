package com.baghdad.tudee.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.baghdad.tudee.presentation.navigation.Route
import com.baghdad.tudee.presentation.navigation.TudeeNavHost

@Composable
fun NavigationContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Route
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        TudeeNavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
    }
}