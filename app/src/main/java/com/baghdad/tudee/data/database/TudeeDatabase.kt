package com.baghdad.tudee.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.baghdad.tudee.data.database.dao.CategoryDao
import com.baghdad.tudee.data.database.dao.TaskDao
import com.baghdad.tudee.data.model.CategoryDto
import com.baghdad.tudee.data.model.TaskDto

@Database(
    entities = [TaskDto::class, CategoryDto::class],
    version = 1
)
abstract class TudeeDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        const val DATABASE_NAME = "tudee_database"
        const val TASKS_TABLE_NAME = "tasks"
        const val CATEGORIES_TABLE_NAME = "categories"
    }
}