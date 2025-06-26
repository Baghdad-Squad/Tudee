package com.baghdad.tudee.ui.screens.homeScreen

sealed interface HomeScreenEffect {
        data class NavigateToTasksScreen(
            val taskState: TaskState
        ): HomeScreenEffect
    }
