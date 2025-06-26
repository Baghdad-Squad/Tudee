package com.baghdad.tudee.ui.screens.categoryTasksScreen

sealed interface CategoryTasksScreenEffect {
    data object NavigateToCategoriesScreen : CategoryTasksScreenEffect
}