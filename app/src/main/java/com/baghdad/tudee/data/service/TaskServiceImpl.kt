package com.baghdad.tudee.data.service

import android.database.sqlite.SQLiteDatabaseCorruptException
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteFullException
import com.baghdad.tudee.data.database.dao.TaskDao
import com.baghdad.tudee.data.model.TaskDto
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.domain.exception.DatabaseCorruptException
import com.baghdad.tudee.domain.exception.DatabaseException
import com.baghdad.tudee.domain.exception.StorageFullException
import com.baghdad.tudee.domain.exception.TudeeException
import com.baghdad.tudee.domain.service.TaskService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class TaskServiceImpl(
    private val taskDao: TaskDao
) : TaskService , BaseService() {
    override suspend fun getTasksByCategory(categoryId: String): Flow<List<Task>> {
        return executeWithErrorHandling {
            taskDao.getTasksByCategory(categoryId)
        }
    }

    override suspend fun getTasksByDate(date: String): Flow<List<Task>> {
        return taskDao.getTasksByDate(date)
    }

    override suspend fun createTask(task: TaskDto) {
        executeWithErrorHandling {
            taskDao.createTask(task)
        }
    }

    override suspend fun editTask(task: TaskDto) {
        executeWithErrorHandling {
            taskDao.editTask(task)
        }
    }

    override suspend fun deleteTask(taskId: String) {
        executeWithErrorHandling {
            taskDao.deleteTask(taskId)
        }
    }

}


