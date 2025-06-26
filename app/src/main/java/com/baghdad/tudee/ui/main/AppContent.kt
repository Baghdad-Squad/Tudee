package com.baghdad.tudee.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.baghdad.tudee.ui.composable.BottomNavigation
import com.baghdad.tudee.ui.navigation.NavigationContent
import com.baghdad.tudee.ui.navigation.rememberStartDestination

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

