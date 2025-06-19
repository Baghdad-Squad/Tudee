package com.baghdad.tudee.data.database.dao

import android.database.sqlite.SQLiteException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.baghdad.tudee.data.database.TudeeDatabase
import com.baghdad.tudee.data.model.AppConfigurationDto
import com.baghdad.tudee.data.model.AppConfigurationDto.Companion.DEFAULT_ID
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.jvm.java

@RunWith(AndroidJUnit4::class)
@SmallTest
class AppConfigurationDaoTest {
    private lateinit var database: TudeeDatabase
    private lateinit var appConfigurationDao: AppConfigurationDao

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TudeeDatabase::class.java
        ).allowMainThreadQueries().build()

        appConfigurationDao = database.appConfigurationDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun shouldEmitTrue_whenDatabaseIsSetToDarkMode() = runTest {
        val config = AppConfigurationDto(id = DEFAULT_ID, isDarkTheme = true)
        appConfigurationDao.insert(config)

        val result = appConfigurationDao.isDarkTheme().first()

        assertThat(result).isTrue()
    }

    @Test
    fun shouldEmitFalse_whenDatabaseIsSetToLightMode() = runTest {
        val config = AppConfigurationDto(id = DEFAULT_ID, isDarkTheme = false)
        appConfigurationDao.insert(config)

        val result = appConfigurationDao.isDarkTheme().first()

        assertThat(result).isFalse()
    }

    @Test
    fun shouldEmitNull_whenNoRecordExists() = runTest {
        val result = appConfigurationDao.isDarkTheme().first()

        assertThat(result).isNull()
    }

    @Test
    fun shouldSetToDarkMode_whenUpdateTudeeThemeCalledWithTrue() = runTest {
        val initialConfig = AppConfigurationDto(id = DEFAULT_ID, isDarkTheme = false)
        appConfigurationDao.insert(initialConfig)

        appConfigurationDao.updateTudeeTheme(true)

        val updatedConfig = appConfigurationDao.isDarkTheme().first()
        assertThat(updatedConfig).isTrue()
    }

    @Test
    fun shouldSetToLightMode_whenUpdateTudeeThemeCalledWithFalse() = runTest {
        val initialConfig = AppConfigurationDto(id = DEFAULT_ID, isDarkTheme = true)
        appConfigurationDao.insert(initialConfig)

        appConfigurationDao.updateTudeeTheme(false)

        val updatedConfig = appConfigurationDao.isDarkTheme().first()
        assertThat(updatedConfig).isFalse()
    }

    @Test
    fun shouldReturn1_whenUpdateTudeeThemeSuccessfullyUpdatesExistingRecord() = runTest {
        val initialConfig = AppConfigurationDto(id = DEFAULT_ID, isDarkTheme = false)
        appConfigurationDao.insert(initialConfig)

        val result = appConfigurationDao.updateTudeeTheme(true)

        assertThat(result).isEqualTo(1)
    }

    @Test
    fun shouldReturn0_whenUpdateTudeeThemeCalledOnNonExistentRecord() = runTest {
        val result = appConfigurationDao.updateTudeeTheme(true)

        assertThat(result).isEqualTo(0)
    }

    @Test
    fun shouldReplaceDuplicateInsert_whenRecordWithSameIdExists() = runTest {
        val initialConfig = AppConfigurationDto(id = DEFAULT_ID, isDarkTheme = false)
        appConfigurationDao.insert(initialConfig)

        val duplicateConfig = AppConfigurationDto(id = DEFAULT_ID, isDarkTheme = true)
        appConfigurationDao.insert(duplicateConfig)

        val result = appConfigurationDao.isDarkTheme().first()
        assertThat(result).isTrue()
    }

    @Test
    fun shouldUpdateExistingRecordToDarkMode_whenSaveTudeeThemeCalledWithTrue() = runTest {
        val initialConfig = AppConfigurationDto(id = DEFAULT_ID, isDarkTheme = false)
        appConfigurationDao.insert(initialConfig)

        appConfigurationDao.saveTudeeTheme(true)

        val result = appConfigurationDao.isDarkTheme().first()
        assertThat(result).isTrue()
    }

    @Test
    fun shouldUpdateExistingRecordToLightMode_whenSaveTudeeThemeCalledWithFalse() = runTest {
        val initialConfig = AppConfigurationDto(id = DEFAULT_ID, isDarkTheme = true)
        appConfigurationDao.insert(initialConfig)

        appConfigurationDao.saveTudeeTheme(false)

        val result = appConfigurationDao.isDarkTheme().first()
        assertThat(result).isFalse()
    }

    @Test
    fun shouldInsertNewRecordAsDarkMode_whenSaveTudeeThemeCalledAndNoRecordExists() = runTest {
        appConfigurationDao.saveTudeeTheme(true)

        val result = appConfigurationDao.isDarkTheme().first()
        assertThat(result).isTrue()
    }

    @Test
    fun shouldInsertNewRecordAsLightMode_whenSaveTudeeThemeCalledAndNoRecordExists() = runTest {
        appConfigurationDao.saveTudeeTheme(false)

        val result = appConfigurationDao.isDarkTheme().first()
        assertThat(result).isFalse()
    }
}