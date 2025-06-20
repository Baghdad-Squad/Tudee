package com.baghdad.tudee.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.screens.OnboardingScreen.OnboardingScreen
import com.baghdad.tudee.ui.screens.category.CategoryScreen
import com.baghdad.tudee.ui.screens.categoryTasksScreen.CategoryTasksScreen
import com.baghdad.tudee.ui.screens.tasks.TasksScreen
import kotlin.reflect.typeOf

@Composable
fun TudeeNavHost(
    navController: NavHostController,
    startDestination: Route,
    modifier: Modifier = Modifier,
    ) {

    NavHost(
        modifier = modifier,
        startDestination = startDestination,
        navController = navController
    ) {
        composable<Route.OnboardingScreen> {
            OnboardingScreen (
                onNavigateToHome = {
                    navController.navigate(Route.HomeScreen) {
                        popUpTo(Route.OnboardingScreen) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<Route.TasksScreen>(
            typeMap = mapOf(
                typeOf<Task.State>() to NavType.EnumType(Task.State::class.java),
            )

        ){ navBackStackEntry ->
            val state = navBackStackEntry.toRoute<Route.TasksScreen>().taskState
            TasksScreen(
                initialState = state
            )
        }


        composable<Route.CategoriesScreen> {
            CategoryScreen(
                navigateToCategoryTask = {
                    navController.navigate(Route.CategoryTasksScreen(it))
                }
            )
        }

        composable<Route.HomeScreen> {
            // TODO put the Home Screen Here
            Box (
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(text = "Home Screen")
            }
        }

        composable<Route.CategoryTasksScreen>{
             val categoryId = it.toRoute<Route.CategoryTasksScreen>().categoryId
            CategoryTasksScreen(
                categoryId = categoryId,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}