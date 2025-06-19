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
    @ColumnInfo(name = COLUMN_IMAGE_TYPE)
    val imageType: String,
    @ColumnInfo(name = COLUMN_IMAGE_DATA)
    val imageData: String = "",
    @ColumnInfo(name = COLUMN_IMAGE_BYTES, typeAffinity = ColumnInfo.BLOB)
    val imageBytes: ByteArray = byteArrayOf()
) {
    companion object {
        const val CATEGORIES_TABLE_NAME = "categories"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_IMAGE_TYPE = "image_type"
        const val COLUMN_IMAGE_DATA = "image_data"
        const val COLUMN_IMAGE_BYTES = "image_bytes"

        const val IMAGE_TYPE_PREDEFINED = "PREDEFINED"
        const val IMAGE_TYPE_BYTE_ARRAY = "BYTE_ARRAY"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CategoryDto

        if (id != other.id) return false
        if (title != other.title) return false
        if (imageType != other.imageType) return false
        if (!imageBytes.contentEquals(other.imageBytes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + imageType.hashCode()
        result = 31 * result + (imageBytes?.contentHashCode() ?: 0)
        return result
    }
}
