package com.baghdad.tudee.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.baghdad.tudee.data.database.dao.AppConfigurationDao
import com.baghdad.tudee.data.database.dao.CategoryDao
import com.baghdad.tudee.data.database.dao.TaskDao
import com.baghdad.tudee.data.model.AppConfigurationDto
import com.baghdad.tudee.data.model.CategoryDto
import com.baghdad.tudee.data.model.TaskDto

@Database(
    entities = [TaskDto::class, CategoryDto::class, AppConfigurationDto::class],
    version = 1
)
abstract class TudeeDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoryDao
    abstract fun appConfigurationDao(): AppConfigurationDao

    companion object {
        const val DATABASE_NAME = "tudee_database"
    }
}