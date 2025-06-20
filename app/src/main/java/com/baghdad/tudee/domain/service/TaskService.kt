package com.baghdad.tudee.domain.service

import com.baghdad.tudee.domain.entity.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate


interface TaskService {
    suspend fun getTasksByCategory(categoryId: Long): Flow<List<Task>>
    suspend fun getTasksByDate(date: LocalDate): Flow<List<Task>>
    suspend fun createTask(task: Task)
    suspend fun editTask(task: Task)
    suspend fun deleteTask(taskId: Long)
}