package com.baghdad.tudee.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.baghdad.tudee.data.model.TaskDto.Companion.TASKS_TABLE_NAME

@Entity(tableName = TASKS_TABLE_NAME)
data class TaskDto(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = COLUMN_ID)
    val id: String,
    @ColumnInfo(name = COLUMN_TITLE)
    val title: String,
    @ColumnInfo(name = COLUMN_DESCRIPTION)
    val description: String,
    @ColumnInfo(name = COLUMN_DATE)
    val date: String,
    @ColumnInfo(name = COLUMN_PRIORITY)
    val priority: String,
    @ColumnInfo(name = COLUMN_CATEGORY_ID)
    val categoryId: String,
    @ColumnInfo(name = COLUMN_STATE)
    val state: String
) {
    companion object {
        const val TASKS_TABLE_NAME = "tasks"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_DATE = "date"
        const val COLUMN_PRIORITY = "priority"
        const val COLUMN_CATEGORY_ID = "category_id"
        const val COLUMN_STATE = "state"
    }
}
