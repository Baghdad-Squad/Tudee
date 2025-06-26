package com.baghdad.tudee.presentation.screens.tasks

import com.baghdad.tudee.domain.entity.Task
import kotlinx.datetime.LocalDate

interface TasksInteractionListener {
    fun onTabSelected(selectedTab: Task.State)
    fun onDateSelectedFromHorizontalRow(selectedDate: LocalDate)
    fun onDatePickedFromDateDialog(selectedDate: LocalDate)
    fun onDeleteTask(task: Task)
    fun onPreviousMonthArrowClick()
    fun onNextMonthArrowClick()
    fun toggleAddEditTaskDialog(initialTask: Long? = null)
    fun toggleTaskDetailsDialog(selectedTaskId: Task? = null)
    fun updateTaskState(taskId: Long, newState: Task.State)
}