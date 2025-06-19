package com.baghdad.tudee.ui.screens.categoryTasksScreen

import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.domain.entity.Task

data class CategoryTasksScreenUiState(
    val inProgressTasks: List<Task> = emptyList(),
    val todoTasks: List<Task> = emptyList(),
    val doneTasks: List<Task> = emptyList(),
    val selectedTab: Task.State = Task.State.TODO,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val categoryName: String = "",
    val categoryImage: Category.Image = Category.Image.Predefined(Category.PredefinedType.EDUCATION),
    val isPredefinedCategory: Boolean = true
)