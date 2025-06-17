package com.baghdad.tudee.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.baghdad.tudee.data.database.TudeeDatabase

@Entity(tableName = TudeeDatabase.CATEGORIES_TABLE_NAME)
data class CategoryDto(
    @PrimaryKey(autoGenerate = false) val id: String,
    val title: String,
    val imageUri: String
)
