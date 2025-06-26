package com.baghdad.tudee.ui.screens.tasks

data class TasksScreenState(
    val tasksUiState: TasksUiState = TasksUiState(),
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String? = null,
)
