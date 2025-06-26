package com.baghdad.tudee.presentation.screens.categories

import com.baghdad.tudee.presentation.composable.bottomSheet.category.AddEditCategorySheetUiState
import com.baghdad.tudee.presentation.model.CategoryUiState

data class CategoriesScreenState(
    val categories: List<CategoryUiState> = emptyList(),
    val addCategorySheetState: AddEditCategorySheetUiState = AddEditCategorySheetUiState()
) {
    val isLoading: Boolean
        get() = categories.isEmpty()
}