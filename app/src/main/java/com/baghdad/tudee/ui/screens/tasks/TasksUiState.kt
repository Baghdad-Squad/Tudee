package com.baghdad.tudee.ui.screens.tasks

import androidx.compose.ui.graphics.painter.Painter
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.R
import kotlinx.datetime.LocalDate

data class TasksUiState(
    val tasks: List<TaskUi> = emptyList(),

    val selectedTab: Task.State = Task.State.TODO,
    val selectedDate: LocalDate = LocalDate(2023, 1, 1)
)

data class TaskUi(
    val title: String,
    val description: String,
    val priority: Task.Priority,
    val icon: Int
)
