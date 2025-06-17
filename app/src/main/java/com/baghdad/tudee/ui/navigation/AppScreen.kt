package com.baghdad.tudee.ui.navigation

import com.baghdad.tudee.R


sealed class AppScreen (
    val route : String,
    val selectedIcon: Int? = null,
    val unSelectedIcon: Int? = null,
){
    data object OnBoardingScreen : AppScreen(route = "on_boarding_screen")

    data object HomeScreen : AppScreen(
        route = "home_screen",
        selectedIcon = R.drawable.ic_blue_home,
        unSelectedIcon = R.drawable.ic_black_home,
    )

    data object TasksScreen : AppScreen(
        route = "tasks_screen",
        selectedIcon = R.drawable.ic_blue_note,
        unSelectedIcon = R.drawable.ic_black_note,
        )

    data object CategoriesScreen : AppScreen(
        route = "categories_screen",
        selectedIcon = R.drawable.ic_blue_menu,
        unSelectedIcon = R.drawable.ic_menu_circle,
    )
}


