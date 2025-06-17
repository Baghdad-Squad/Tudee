package com.baghdad.tudee.data.mapper

import com.baghdad.tudee.data.model.TaskDto
import com.baghdad.tudee.domain.entity.Task
import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun TaskDto.toEntity(): Task = Task(
    id = Uuid.parse(this.id),
    title = this.title,
    description = this.description,
    state = Task.State.valueOf(this.state),
    date = LocalDate.parse(this.date),
    categoryId = Uuid.parse(this.categoryId),
    priority = Task.Priority.valueOf(this.priority)
)

@OptIn(ExperimentalUuidApi::class)
fun Task.toDto():TaskDto = TaskDto(
    id = this.id.toString(),
    title = this.title,
    description = this.description,
    date = this.date.toString(),
    priority = this.priority.toString(),
    categoryId = this.categoryId.toString(),
    state = this.state.toString()
)

fun List<TaskDto>.toEntities(): List<Task> = this.map { it.toEntity() }

fun List<Task>.toDtos(): List<TaskDto> = this.map { it.toDto() }