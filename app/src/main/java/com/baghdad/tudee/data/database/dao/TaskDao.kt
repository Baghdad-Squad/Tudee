package com.baghdad.tudee.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.baghdad.tudee.data.model.TaskDto
import com.baghdad.tudee.data.model.TaskDto.Companion.COLUMN_CATEGORY_ID
import com.baghdad.tudee.data.model.TaskDto.Companion.COLUMN_DATE
import com.baghdad.tudee.data.model.TaskDto.Companion.TASKS_TABLE_NAME
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi

@Dao
@OptIn(ExperimentalUuidApi::class)
interface TaskDao {

    @Query("SELECT * FROM $TASKS_TABLE_NAME WHERE $COLUMN_CATEGORY_ID = :categoryId")
    fun getTasksByCategory(categoryId: Long): Flow<List<TaskDto>>


    @Query("SELECT * FROM $TASKS_TABLE_NAME WHERE $COLUMN_DATE = :date")
    fun getTasksByDate(date: String): Flow<List<TaskDto>>

    @Insert
    suspend fun createTask(task: TaskDto)

    @Update
    suspend fun editTask(task: TaskDto)


    @Delete
    suspend fun deleteTask(taskId: Long)
}