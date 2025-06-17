package com.baghdad.tudee.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.baghdad.tudee.data.database.TudeeDatabase

@Entity(tableName = TudeeDatabase.TASKS_TABLE_NAME)
data class TaskDto(
    @PrimaryKey(autoGenerate = false) val id: String,
    val title: String,
    val description: String,
    val date: String,
    val priority: String,
    val categoryId: String,
    val state: String
)
