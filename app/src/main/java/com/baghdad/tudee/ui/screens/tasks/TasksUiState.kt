package com.baghdad.tudee.ui.screens.tasks

import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.domain.entity.Task
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

data class TasksUiState(

    val tasksOnSpecificDate : List<Task> = emptyList(),
    val selectedTab: Task.State = Task.State.IN_PROGRESS,
    val selectedDate: LocalDate? = null,
    val monthDates: List<LocalDate> = emptyList(),
    val tasksDisplayed : List<Task> = emptyList(),
    val categories: List<Category> = emptyList(),
    val currentMonth : Month = Month.JUNE,
    val currentYear : Int = 2025,
    val showAddNewTask: Boolean= false
)


