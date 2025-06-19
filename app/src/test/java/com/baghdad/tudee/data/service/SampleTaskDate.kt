package com.baghdad.tudee.data.service

import com.baghdad.tudee.data.model.TaskDto
import com.baghdad.tudee.domain.entity.Task
import kotlinx.datetime.LocalDate

object SampleTaskData{
    val categoryID: Long = 10
    val oneTaskExpected = 1
    val date = "2025-06-17"
    val index = 0
    val expectedTitle = "Test Task"
    val expectedDescription = "desc"
    val priorty = "LOW"
    val taskID = 1L
    val dbError = "DB error"

    val sampleDto = TaskDto(
        id = 1,
        title = "Test Task",
        description = "desc",
        date = "2025-06-17",
        priority = "LOW",
        categoryId = 10,
        state = "TODO"
    )

    val sampleTask = Task(
        id = 1,
        title = "Test Task",
        description = "desc",
        date = LocalDate.parse("2025-06-17"),
        priority = Task.Priority.LOW,
        categoryId = 10,
        state = Task.State.TODO
    )
}