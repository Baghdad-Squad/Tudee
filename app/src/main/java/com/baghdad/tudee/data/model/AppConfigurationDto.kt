package com.baghdad.tudee.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.baghdad.tudee.data.model.AppConfigurationDto.Companion.APP_CONFIGURATION_TABLE_NAME

@Entity(tableName = APP_CONFIGURATION_TABLE_NAME)
data class AppConfigurationDto(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = COLUMN_ID)
    val id: Int = DEFAULT_ID,
    @ColumnInfo(name = COLUMN_IS_DARK_THEME)
    val isDarkTheme: Boolean = false
) {
    companion object {
        const val APP_CONFIGURATION_TABLE_NAME = "app_configuration"
        const val COLUMN_IS_DARK_THEME = "is_dark_theme"
        const val COLUMN_ID = "id"
        const val DEFAULT_ID = 1
    }
}