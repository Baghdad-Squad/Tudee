package com.baghdad.tudee.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.baghdad.tudee.data.model.CategoryDto
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import com.baghdad.tudee.data.model.CategoryDto.Companion.CATEGORIES_TABLE_NAME
import com.baghdad.tudee.data.model.TaskDto.Companion.COLUMN_CATEGORY_ID
import com.baghdad.tudee.data.model.TaskDto.Companion.TASKS_TABLE_NAME

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createCategory(category: CategoryDto)

    @Query("SELECT * FROM $CATEGORIES_TABLE_NAME")
    fun getCategories(): Flow<List<CategoryDto>>

    @Update
    suspend fun updateCategory(category: CategoryDto)


    @Query("DELETE FROM $CATEGORIES_TABLE_NAME WHERE id = :id")
    suspend fun deleteCategory(id: Long)
}