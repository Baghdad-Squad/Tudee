package com.baghdad.tudee.ui.screens.categoryTasksScreen

import com.baghdad.tudee.domain.entity.Task

data class CategoryTasksScreenUiState (
    val categoryName: String = "",
    val inProgressTasks: List<Task> = emptyList(),
    val todoTasks: List<Task> = emptyList(),
    val doneTasks: List<Task> = emptyList(),
    val selectedTab: Task.State = Task.State.TODO,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,

)