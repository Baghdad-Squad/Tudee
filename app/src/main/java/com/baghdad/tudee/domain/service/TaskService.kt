package com.baghdad.tudee.domain.service

import com.baghdad.tudee.domain.entity.Task
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.util.UUID

interface TaskService {


   suspend fun getTasksByCategory(categoryId: UUID): Flow<List<Task>>
   suspend fun getTasksByDate(date: LocalDate): Flow<List<Task>>
   suspend fun createTask(task: Task)
   suspend fun editTask(task: Task)
   suspend fun deleteTask(taskId: UUID)
}