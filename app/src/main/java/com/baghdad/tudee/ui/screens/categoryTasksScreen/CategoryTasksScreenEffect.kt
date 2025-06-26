package com.baghdad.tudee.ui.screens.categoryTasksScreen

sealed interface CategoryTasksScreenEffect {
    data class NavigateToCategoriesScreenWithResult(
        val message: Int,
        val isSuccess: Boolean
    ) : CategoryTasksScreenEffect
}