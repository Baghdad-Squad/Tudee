package com.baghdad.tudee.data.model

import java.time.LocalDate
import java.util.UUID

data class TaskDto(
    val id: UUID,
    val taskTitle: String,
    val description: String,
    val date: LocalDate,
    val priority: Priority,
    val categoryId: UUID,
    val state: TaskState
)
