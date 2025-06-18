package com.baghdad.tudee.ui.navigation

import com.baghdad.tudee.R
import com.baghdad.tudee.domain.entity.Task

enum class BottomNavigationRoute(
    val route: Route,
    val selectedIcon: Int? = null,
    val unSelectedIcon: Int? = null,
) {
    HOME_SCREEN(
        route = Route.HomeScreen,
        selectedIcon = R.drawable.ic_blue_home,
        unSelectedIcon = R.drawable.ic_black_home,
    ),

   TASK_SCREEN(
        route = Route.TasksScreen(taskState = Task.State.IN_PROGRESS,),
        selectedIcon = R.drawable.ic_blue_note,
        unSelectedIcon = R.drawable.ic_black_note,
    ),

    CATEGORIES_SCREEN(
        route = Route.CategoriesScreen,
        selectedIcon = R.drawable.ic_blue_menu,
        unSelectedIcon = R.drawable.ic_menu_circle,
    )
}




