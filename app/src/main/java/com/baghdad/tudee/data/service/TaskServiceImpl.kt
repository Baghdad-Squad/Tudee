package com.baghdad.tudee.data.service

import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.domain.service.TaskService
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.util.UUID

class TaskServiceImpl(): TaskService {
    override suspend fun getTasksByCategory(categoryId: UUID): Flow<List<Task>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTasksByDate(date: LocalDate): Flow<List<Task>> {
        TODO("Not yet implemented")
    }

    override suspend fun createTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun editTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(taskId: UUID) {
        TODO("Not yet implemented")
    }
}