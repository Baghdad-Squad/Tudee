package com.baghdad.tudee.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
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
    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        NavigationContent(
            modifier.weight(1f).
             fillMaxWidth(),
            navController,
            startDestination
        )
        BottomNavigation(navController = navController)
    }
}