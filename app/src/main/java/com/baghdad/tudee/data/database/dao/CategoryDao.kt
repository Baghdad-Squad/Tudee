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


@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createCategory(category: CategoryDto)

    @Query("SELECT * FROM categories")
    fun getCategories(): Flow<List<CategoryDto>>

    @Update
    suspend fun updateCategory(category: CategoryDto)


    @Delete
    suspend fun deleteCategory(id: String)
}