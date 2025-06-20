package com.baghdad.tudee.data.service

import com.baghdad.tudee.data.database.dao.AppConfigurationDao
import com.baghdad.tudee.domain.exception.TudeeException
import com.baghdad.tudee.domain.service.AppConfigurationService
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AppConfigurationServiceImplTest {

    private lateinit var mockDao: AppConfigurationDao
    private lateinit var service: AppConfigurationService

    @Before
    fun setUp() {
        mockDao = mockk()
        service = AppConfigurationServiceImpl(mockDao)
    }

    @Test
    fun `isDarkTheme returns correct value`() = runTest {
        every { mockDao.isDarkTheme() } returns flowOf(true)

        val result = service.isDarkTheme().first()

        assertTrue(result)
    }

    @Test
    fun `isDarkTheme handles exception correctly`() = runTest {
        every { mockDao.isDarkTheme() } throws RuntimeException("Unknown error")

        val result = runCatching {
            service.isDarkTheme().toList()
        }

        assertTrue(result.exceptionOrNull() is TudeeException)
    }

    @Test
    fun `setTheme calls saveTudeeTheme with correct value on success`() = runTest {
        val isDark = true
        coEvery { mockDao.saveTudeeTheme(isDark) } just Runs

        service.setTheme(isDark)

        coVerify { mockDao.saveTudeeTheme(isDark) }
    }
}