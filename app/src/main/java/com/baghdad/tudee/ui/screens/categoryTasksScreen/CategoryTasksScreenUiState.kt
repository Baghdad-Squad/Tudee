package com.baghdad.tudee.ui.screens.categoryTasksScreen

import com.baghdad.tudee.ui.shared.components.category.AddEditCategorySheetUiState
import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.domain.entity.Task

data class CategoryTasksScreenUiState(
    val inProgressTasks: List<Task> = emptyList(),
    val todoTasks: List<Task> = emptyList(),
    val doneTasks: List<Task> = emptyList(),
    val selectedTab: Task.State = Task.State.IN_PROGRESS,
    val category: Category = Category(
        id = 0L,
        title = "",
        image = Category.Image.Predefined(Category.PredefinedType.ENTERTAINMENT)
    ),
    val addEditCategorySheetState: AddEditCategorySheetUiState = AddEditCategorySheetUiState(
        isEditing = true
    ),
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
)