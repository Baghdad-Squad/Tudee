package com.baghdad.tudee.data.service

import com.baghdad.tudee.data.database.dao.AppConfigurationDao
import com.baghdad.tudee.domain.exception.TudeeException
import com.baghdad.tudee.domain.service.AppConfigurationService
import io.mockk.every
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
        //given
        every { mockDao.isDarkTheme() } returns flowOf(true)

        //when
        val result = service.isDarkTheme().first()

        //then
        assertTrue(result)
    }

    @Test
    fun `isDarkTheme handles exception correctly`() = runTest {
        //given
        every { mockDao.isDarkTheme() } throws RuntimeException("Unknown error")

        //when
        val result = runCatching {
            service.isDarkTheme().toList()
        }

        //then
        assertTrue(result.exceptionOrNull() is TudeeException)
    }
}