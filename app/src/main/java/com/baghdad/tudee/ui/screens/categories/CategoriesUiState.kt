package com.baghdad.tudee.ui.screens.categories

import com.baghdad.tudee.ui.composable.bottomSheet.category.AddEditCategorySheetUiState
import com.baghdad.tudee.ui.model.CategoryUiState

data class CategoriesUiState(
    val categories: List<CategoryUiState> = emptyList(),
    val addCategorySheetState: AddEditCategorySheetUiState = AddEditCategorySheetUiState()
)
