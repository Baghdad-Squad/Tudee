package com.baghdad.tudee.ui.screens.tasks

import com.baghdad.tudee.domain.entity.Task
import kotlinx.datetime.LocalDate

interface TasksInteractionHandler {
    fun onTabSelected(selectedTab: Task.State)
    fun onDateSelectedFromHorizontalRow(selectedDate: LocalDate)
    fun onDatePickedFromDateDialog(selectedDate: LocalDate)
    fun onDeleteTask(task: Task)
    fun onPreviousMonthArrowClick()
    fun onNextMonthArrowClick()
}