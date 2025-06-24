package com.baghdad.tudee.ui.screens.categories

sealed class CategoriesUiEffect {
    data class NavigateToCategoryTasks(
        val categoryId: Long
    ): CategoriesUiEffect()
}