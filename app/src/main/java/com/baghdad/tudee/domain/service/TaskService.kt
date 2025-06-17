package com.baghdad.tudee.domain.service

import com.baghdad.tudee.data.model.TaskDto
import com.baghdad.tudee.domain.entity.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi

import kotlin.uuid.Uuid
@OptIn(ExperimentalUuidApi::class)
interface TaskService {


   suspend fun getTasksByCategory(categoryId: String): Flow<List<Task>>
   suspend fun getTasksByDate(date: String): Flow<List<Task>>
   suspend fun createTask(task: TaskDto)
   suspend fun editTask(task: TaskDto)
   suspend fun deleteTask(taskId: String)
}