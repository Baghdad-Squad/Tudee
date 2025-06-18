package com.baghdad.tudee.data.service

import com.baghdad.tudee.data.database.dao.TaskDao
import com.baghdad.tudee.data.mapper.toDto
import com.baghdad.tudee.data.mapper.toEntities
import com.baghdad.tudee.data.model.TaskDto
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.domain.service.TaskService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

class TaskServiceImpl(
    private val taskDao: TaskDao
) : TaskService, BaseService() {
    override suspend fun getTasksByCategory(categoryId: Long): Flow<List<Task>> =
        executeWithErrorHandling {
            taskDao.getTasksByCategory(categoryId).map(List<TaskDto>::toEntities)
        }

    override suspend fun getTasksByDate(date: LocalDate): Flow<List<Task>> =
        executeWithErrorHandling {
            taskDao.getTasksByDate(date.toString()).map(List<TaskDto>::toEntities)
        }

    override suspend fun createTask(task: Task) = executeWithErrorHandling {
        taskDao.createTask(task.toDto())
    }

    override suspend fun editTask(task: Task) = executeWithErrorHandling {
        taskDao.editTask(task.toDto())
    }

    override suspend fun deleteTask(taskId: Long) = executeWithErrorHandling {
        taskDao.deleteTask(taskId)
    }
}