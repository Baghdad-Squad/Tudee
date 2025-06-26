package com.baghdad.tudee.presentation.screens.tasks

import com.baghdad.tudee.domain.entity.Task

interface AddEditTaskInteractionListener {
    fun onClickSaveTask(task: Task)
}