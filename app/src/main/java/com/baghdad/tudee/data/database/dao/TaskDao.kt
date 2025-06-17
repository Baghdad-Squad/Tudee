package com.baghdad.tudee.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.baghdad.tudee.data.model.TaskDto
import com.baghdad.tudee.domain.entity.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Dao
@OptIn(ExperimentalUuidApi::class)
interface TaskDao {

    @Query("SELECT * FROM tasks WHERE categoryId = :categoryId")
    suspend fun getTasksByCategory(categoryId: String): Flow<List<Task>>


    @Query("SELECT * FROM tasks WHERE date = :date")
    suspend fun getTasksByDate(date: String ): Flow<List<Task>>


    @Insert
    suspend fun createTask(task: TaskDto)

    @Update
    suspend fun editTask(task: TaskDto)


    @Delete
    suspend fun deleteTask(taskId: String)
}