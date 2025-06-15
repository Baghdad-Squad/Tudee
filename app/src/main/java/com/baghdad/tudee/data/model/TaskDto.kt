package com.baghdad.tudee.data.model

data class TaskDto(
    val id: String,
    val taskTitle: String,
    val description: String,
    val date: String,
    val priority: String,
    val categoryId: String,
    val state: String
)
