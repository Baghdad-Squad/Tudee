package com.baghdad.tudee.ui.screens.categories

import com.baghdad.tudee.ui.model.CategoryUiState
import com.baghdad.tudee.ui.shared.components.category.AddEditCategorySheetUiState

data class CategoriesScreenState(
    val categories: List<CategoryUiState> = emptyList(),
    val addCategorySheetState: AddEditCategorySheetUiState = AddEditCategorySheetUiState()
) {
    val isLoading: Boolean
        get() = categories.isEmpty()
}