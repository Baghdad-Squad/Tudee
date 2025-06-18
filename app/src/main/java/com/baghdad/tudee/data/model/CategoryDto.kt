package com.baghdad.tudee.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.baghdad.tudee.data.model.CategoryDto.Companion.CATEGORIES_TABLE_NAME

@Entity(tableName = CATEGORIES_TABLE_NAME)
data class CategoryDto(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val id: Long,
    @ColumnInfo(name = COLUMN_TITLE)
    val title: String,
    @ColumnInfo(name = COLUMN_IMAGE_URI)
    val imageUri: String
) {
    companion object {
        const val CATEGORIES_TABLE_NAME = "categories"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_IMAGE_URI = "image_uri"
    }
}
