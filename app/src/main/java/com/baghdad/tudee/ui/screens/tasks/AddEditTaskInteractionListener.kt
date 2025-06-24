package com.baghdad.tudee.ui.screens.tasks

import com.baghdad.tudee.domain.entity.Task

interface AddEditTaskInteractionListener {
    fun onClickSaveTask(task: Task)
    fun editTask(task: Task)
}