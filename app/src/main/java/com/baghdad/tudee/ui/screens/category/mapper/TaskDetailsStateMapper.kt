package com.baghdad.tudee.ui.screens.category.mapper

import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.screens.homeScreen.TaskDetailsState
import com.baghdad.tudee.ui.utils.now
import kotlinx.datetime.LocalDate


fun TaskDetailsState.toTask(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        date = LocalDate.now(),
        categoryId = id ?: -1L,
        priority = taskPriority,
        state = taskState
    )
}