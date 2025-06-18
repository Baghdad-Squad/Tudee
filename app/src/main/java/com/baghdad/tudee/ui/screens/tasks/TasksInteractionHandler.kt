package com.baghdad.tudee.ui.screens.tasks

import com.baghdad.tudee.domain.entity.Task

interface TasksInteractionHandler {
    fun onTabSelected(selectedTab: Task.State)
    fun onDateSelected(selectedDate: String)
    fun onDeleteTask(task: Task)
}