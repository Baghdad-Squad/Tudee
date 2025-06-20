package com.baghdad.tudee.ui.screens.tasks

import com.baghdad.tudee.domain.entity.Task

interface AddEditTaskInteractionListener {
    fun onClickAddNewTask(task: Task)
}