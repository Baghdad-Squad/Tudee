package com.baghdad.tudee.ui.screens.homeScreen

import androidx.annotation.DrawableRes
import com.baghdad.tudee.R
import com.baghdad.tudee.domain.entity.Task

data class HomeScreenUIState(
    val inProgressTasks: List<Task> = emptyList(),
    val todoTasks: List<Task> = emptyList(),
    val doneTasks: List<Task> = emptyList(),
    val errorMessage: String? = null,
    val sliderState: SliderState = SliderState.NOTHING_IN_YOUR_LIST,
    val taskDetailsState: TaskDetailsState = TaskDetailsState(),
    val taskBottomSheetState: TaskBottomSheetState = TaskBottomSheetState(),
    val isDark: Boolean = false,
    val addNewTask: Boolean = false,
    val editTask: Boolean = false,
    val taskDetails: Boolean = false,
    val showSnackBar: SnackBarState = SnackBarState(),
    val isLoading: Boolean = false,

)


data class SnackBarState(
    val message: String = "",
    val isError: Boolean = false,
    val isVisible: Boolean = false
)


data class TaskDetailsState(
    @DrawableRes val icon: Int = R.drawable.ic_book_open,
    val title: String = "",
    val description: String = "",
    val taskState: TaskState = TaskState.TODO,
    val taskPriority: Task.Priority = Task.Priority.LOW,
)

data class TaskBottomSheetState(
    val taskId: Long = -1,
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val priority: Task.Priority = Task.Priority.LOW,
    val categoryId: Long = -1L,
    val isEditMode: Boolean = false
)


enum class SliderState{
    STAY_WORKING,
    TADOO,
    ZERO_PROGRESS,
    NOTHING_IN_YOUR_LIST
}