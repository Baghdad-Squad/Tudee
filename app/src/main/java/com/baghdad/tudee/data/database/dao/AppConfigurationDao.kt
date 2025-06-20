package com.baghdad.tudee.data.database.dao

import android.provider.SyncStateContract.Helpers.insert
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.baghdad.tudee.data.model.AppConfigurationDto
import com.baghdad.tudee.data.model.AppConfigurationDto.Companion.APP_CONFIGURATION_TABLE_NAME
import com.baghdad.tudee.data.model.AppConfigurationDto.Companion.COLUMN_IS_DARK_THEME
import com.baghdad.tudee.data.model.AppConfigurationDto.Companion.COLUMN_IS_FIRST_LAUNCH
import com.baghdad.tudee.data.model.AppConfigurationDto.Companion.DEFAULT_ID
import kotlinx.coroutines.flow.Flow

@Dao
interface AppConfigurationDao {
    @Query("UPDATE $APP_CONFIGURATION_TABLE_NAME SET $COLUMN_IS_DARK_THEME = :isDarkTheme WHERE id = $DEFAULT_ID")
    suspend fun updateTudeeTheme(isDarkTheme: Boolean): Int

    @Query("SELECT $COLUMN_IS_DARK_THEME FROM $APP_CONFIGURATION_TABLE_NAME WHERE id = $DEFAULT_ID")
    fun isDarkTheme(): Flow<Boolean?>


    @Insert(onConflict = OnConflictStrategy.REPLACE)

    @Query("SELECT $COLUMN_IS_FIRST_LAUNCH FROM $APP_CONFIGURATION_TABLE_NAME WHERE id = $DEFAULT_ID")
    fun isFirstLaunch(): Boolean?

    @Query("UPDATE $APP_CONFIGURATION_TABLE_NAME SET $COLUMN_IS_FIRST_LAUNCH = :isFirstLaunch WHERE id = $DEFAULT_ID")
    suspend fun updateIsFirstLaunch(isFirstLaunch: Boolean): Int

    @Transaction
    suspend fun setIsFirstLaunch() {
        updateIsFirstLaunch(false)
            .takeIf { it == 0 }
            ?.let{ insert(AppConfigurationDto(id = DEFAULT_ID, isFirstLaunch = false)) }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(config: AppConfigurationDto)

    @Transaction
    suspend fun saveTudeeTheme(isDarkTheme: Boolean) {
        updateTudeeTheme(isDarkTheme)
            .takeIf { it == 0 }
            ?.let { insert(AppConfigurationDto(id = DEFAULT_ID, isDarkTheme = isDarkTheme)) }
    }
}