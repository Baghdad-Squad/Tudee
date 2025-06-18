package com.baghdad.tudee.ui.screens.homeScreen

import com.baghdad.tudee.domain.entity.Task

data class HomeScreenUIState(
    val inProgressTasks: List<Task> = emptyList(),
    val todoTasks: List<Task> = emptyList(),
    val doneTasks: List<Task> = emptyList(),
    val errorMessage: String? = null,
    val showAddBottomSheet: Boolean = false,
    val showAddCategoryDialog: Boolean = false,
    val showSnackBar: Boolean = false,
    val isLoading: Boolean = false,

)
