package com.baghdad.tudee.presentation.screens.categories

sealed interface CategoriesScreenEffect {
    data class NavigateToCategoryTasks(
        val categoryId: Long
    ): CategoriesScreenEffect
}