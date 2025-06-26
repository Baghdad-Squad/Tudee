package com.baghdad.tudee.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.baghdad.tudee.presentation.composable.BottomNavigation
import com.baghdad.tudee.presentation.navigation.NavigationContent
import com.baghdad.tudee.presentation.navigation.rememberStartDestination

@Composable
fun AppContent(
    modifier: Modifier = Modifier,
    isFirstLaunch: Boolean,
    navController: NavHostController
) {
    val startDestination = rememberStartDestination(isFirstLaunch)
    NavigationContent(modifier, navController, startDestination)
    BottomNavigation(navController = navController)
}

