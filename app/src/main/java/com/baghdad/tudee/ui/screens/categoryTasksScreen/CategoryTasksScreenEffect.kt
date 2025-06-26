package com.baghdad.tudee.ui.screens.categoryTasksScreen

sealed interface CategoryTasksScreenEffect {
    data object OnCategoryDeleted : CategoryTasksScreenEffect
}