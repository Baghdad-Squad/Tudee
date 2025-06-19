package com.baghdad.tudee.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.baghdad.tudee.domain.entity.Task
import kotlin.reflect.typeOf

@Composable
fun TudeeNavHost(navController: NavHostController, startDestination: Route) {

    NavHost(
        startDestination = startDestination,
        navController = navController
    ) {
        composable<Route.OnboardingScreen> {
            /* TODO put the onBoarding Screen Here*/
        }

        composable<Route.TasksScreen>(
            typeMap = mapOf(
                typeOf<Task.State>() to NavType.EnumType(Task.State::class.java),
            )

        ){
            // TODO put the Tasks Screen Here With Argument
        }


        composable<Route.CategoriesScreen> {
            /* TODO put the Categories  Screen Here*/
        }

        composable<Route.HomeScreen> {
            // TODO put the Home Screen Here
        }

        composable<Route.CategoryTasksScreen>{
             val categoryId = it.toRoute<Route.CategoryTasksScreen>().categoryId
            // TODO put the Category Tasks Screen Here

        }
    }
}