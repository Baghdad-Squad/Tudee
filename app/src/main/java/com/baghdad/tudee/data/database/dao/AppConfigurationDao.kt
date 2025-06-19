package com.baghdad.tudee.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.baghdad.tudee.data.model.AppConfigurationDto
import com.baghdad.tudee.data.model.AppConfigurationDto.Companion.APP_CONFIGURATION_TABLE_NAME
import com.baghdad.tudee.data.model.AppConfigurationDto.Companion.COLUMN_IS_DARK_THEME
import com.baghdad.tudee.data.model.AppConfigurationDto.Companion.DEFAULT_ID
import kotlinx.coroutines.flow.Flow

@Dao
interface AppConfigurationDao {
    @Query("UPDATE $APP_CONFIGURATION_TABLE_NAME SET $COLUMN_IS_DARK_THEME = :isDarkTheme WHERE id = $DEFAULT_ID")
    suspend fun updateTudeeTheme(isDarkTheme: Boolean): Int

    @Query("SELECT $COLUMN_IS_DARK_THEME FROM $APP_CONFIGURATION_TABLE_NAME WHERE id = $DEFAULT_ID")
    fun isDarkTheme(): Flow<Boolean?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(config: AppConfigurationDto)

    @Transaction
    suspend fun saveTudeeTheme(isDarkTheme: Boolean) {
        updateTudeeTheme(isDarkTheme)
            .takeIf { it == 0 }
            ?.let { insert(AppConfigurationDto(id = DEFAULT_ID, isDarkTheme = isDarkTheme)) }
    }
}