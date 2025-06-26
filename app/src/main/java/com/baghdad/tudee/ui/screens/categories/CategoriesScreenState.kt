package com.baghdad.tudee.ui.screens.categories

import com.baghdad.tudee.ui.composable.bottomSheet.category.AddEditCategorySheetUiState
import com.baghdad.tudee.ui.model.CategoryUiState

data class CategoriesScreenState(
    val categories: List<CategoryUiState> = emptyList(),
    val addCategorySheetState: AddEditCategorySheetUiState = AddEditCategorySheetUiState()
) {
    val isLoading: Boolean
        get() = categories.isEmpty()
}