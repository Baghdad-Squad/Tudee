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
import com.baghdad.tudee.data.model.CategoryDto.Companion.COLUMN_ID

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createCategory(category: CategoryDto): Long

    @Query("SELECT * FROM $CATEGORIES_TABLE_NAME")
    fun getCategories(): Flow<List<CategoryDto>>

    @Query("SELECT * FROM $CATEGORIES_TABLE_NAME WHERE $COLUMN_ID = :id")
    fun getCategoryById(id: Long): CategoryDto?

    @Update
    suspend fun updateCategory(category: CategoryDto): Int


    @Query("DELETE FROM $CATEGORIES_TABLE_NAME WHERE $COLUMN_ID = :id")
    suspend fun deleteCategory(id: Long): Int
}