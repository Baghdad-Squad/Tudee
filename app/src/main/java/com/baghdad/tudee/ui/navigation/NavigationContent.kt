package com.baghdad.tudee.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun NavigationContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Route
) {
    TudeeNavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    )
}