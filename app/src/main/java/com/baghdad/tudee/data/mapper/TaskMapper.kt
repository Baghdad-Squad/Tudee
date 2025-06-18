package com.baghdad.tudee.data.mapper

import com.baghdad.tudee.data.model.TaskDto
import com.baghdad.tudee.domain.entity.Task
import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

fun TaskDto.toEntity(): Task = Task(
    id = this.id,
    title = this.title,
    description = this.description,
    state = Task.State.valueOf(this.state),
    date = LocalDate.parse(this.date),
    categoryId = this.categoryId,
    priority = Task.Priority.valueOf(this.priority)
)

fun Task.toDto():TaskDto = TaskDto(
    id = this.id,
    title = this.title,
    description = this.description,
    date = this.date.toString(),
    priority = this.priority.toString(),
    categoryId = this.categoryId,
    state = this.state.toString()
)

fun List<TaskDto>.toEntities(): List<Task> = this.map { it.toEntity() }

fun List<Task>.toDtos(): List<TaskDto> = this.map { it.toDto() }