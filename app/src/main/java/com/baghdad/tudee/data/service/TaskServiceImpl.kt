package com.baghdad.tudee.data.service

import android.database.sqlite.SQLiteDatabaseCorruptException
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteFullException
import com.baghdad.tudee.data.database.dao.TaskDao
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
    override suspend fun getTasksByCategory(categoryId: Uuid): Flow<List<Task>> {
        val flow = executeWithErrorHandling {
            taskDao.getTasksByCategory(categoryId)
        }
        return flow.catch { e ->
            throw when (e) {
                is SQLiteFullException -> StorageFullException(e.message)
                is SQLiteDatabaseCorruptException -> DatabaseCorruptException(e.message)
                is SQLiteException -> DatabaseException(e.message)
                else -> TudeeException(e.message)
            }
        }
    }

    override suspend fun getTasksByDate(date: LocalDate): Flow<List<Task>> {
        val flow = executeWithErrorHandling {
            taskDao.getTasksByDate(date)
        }
        return flow.catch { e ->
            throw when (e) {
                is SQLiteFullException -> StorageFullException(e.message)
                is SQLiteDatabaseCorruptException -> DatabaseCorruptException(e.message)
                is SQLiteException -> DatabaseException(e.message)
                else -> TudeeException(e.message)
            }
        }
    }

    override suspend fun createTask(task: Task) {
        executeWithErrorHandling {
            taskDao.createTask(task)
        }
    }

    override suspend fun editTask(task: Task) {
        executeWithErrorHandling {
            taskDao.editTask(task)
        }
    }

    override suspend fun deleteTask(taskId: Uuid) {
        executeWithErrorHandling {
            taskDao.deleteTask(taskId)
        }
    }

}


