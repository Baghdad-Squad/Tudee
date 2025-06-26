package com.baghdad.tudee.ui.screens.categories

sealed interface CategoriesScreenEffect {
    data class NavigateToCategoryTasks(
        val categoryId: Long
    ): CategoriesScreenEffect
}