package com.baghdad.tudee.ui.screens.homeScreen

import com.baghdad.tudee.R
import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.composable.SnackbarState
import com.baghdad.tudee.ui.utils.now
import kotlinx.datetime.LocalDate

data class HomeScreenUIState(
    val inProgressTasks: List<Task> = emptyList(),
    val todoTasks: List<Task> = emptyList(),
    val doneTasks: List<Task> = emptyList(),
    val errorMessage: Int = R.string.empty_string,
    val sliderState: SliderState = SliderState.NOTHING_IN_YOUR_LIST,
    val taskDetailsState: TaskDetailsState = TaskDetailsState(),
    val addTaskState: TaskUIState = TaskUIState(),
    val editTaskState: TaskUIState = TaskUIState(),
    val detailsTaskState: TaskUIState = TaskUIState(),
    val isDark: Boolean = false,
    val showAddNewTask: Boolean = false,
    val showEditTask: Boolean = false,
    val showTaskDetails: Boolean = false,
    val showSnackBar: SnackbarState = SnackbarState(),
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val selectedDate: LocalDate? = null,
    )

data class TaskUIState(
    val id: Long = 0L,
    val title: String = "",
    val description: String = "",
    val date: LocalDate = LocalDate.now(),
    val priority: Task.Priority = Task.Priority.LOW,
    val categoryId: Long = -1L,
    val categories: List<Category> = emptyList(),
    val state: Task.State = Task.State.TODO,
    val currentTask: Task? = null,
    )




data class TaskDetailsState(
    val id: Long = 0L,
    val title: String = "",
    val description: String = "",
    val taskState: Task.State = Task.State.TODO,
    val taskPriority: Task.Priority = Task.Priority.LOW,
    val category: Category? = null
)

fun Task.toTaskDetailsState(category: Category?) = TaskDetailsState(
    id = this.id,
    title = this.title,
    description = this.description,
    taskState = this.state,
    taskPriority = this.priority,
    category = category
)


enum class SliderState{
    STAY_WORKING,
    TADOO,
    ZERO_PROGRESS,
    NOTHING_IN_YOUR_LIST
}

fun TaskUIState.toTask() =
    Task(
        id = this.id,
        title = this.title,
        description = this.description,
        date = this.date,
        priority = this.priority,
        categoryId = this.categoryId,
        state = this.state
    )
