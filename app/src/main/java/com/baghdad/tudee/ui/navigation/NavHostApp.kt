package com.baghdad.tudee.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.baghdad.tudee.ui.screens.homeScreen.HomeScreen
import com.baghdad.tudee.ui.screens.homeScreen.TaskState

@Composable
fun NavHostApp(navController: NavHostController) {

    NavHost(
        startDestination = AppScreen.HomeScreen.route,
        navController = navController
    ) {
        composable(route = AppScreen.OnBoardingScreen.route) {
            /* TODO put the onBoarding Screen Here*/
        }

        composable(
            route = AppScreen.TasksScreen.route,
        ) {
            /* TODO put the Tasks Screen Here*/
        }

        composable(
            route = AppScreen.TasksScreen.route + "/{taskState}",
            arguments = listOf(navArgument("taskState") { type = NavType.StringType })
        ) { backStackEntry ->
            val argumentValue =
                backStackEntry.arguments?.getString("taskState") ?: TaskState.IN_PROGRESS.name
            /* TODO put the Tasks Screen Here and send argumentValue to argument of Tasks Screen*/
        }

        composable(route = AppScreen.CategoriesScreen.route) {
            /* TODO put the Categories  Screen Here*/
        }

        composable(route = AppScreen.HomeScreen.route) {
            // here put the view model and state then send them to parameter of home screen
            HomeScreen()
        }
    }
}