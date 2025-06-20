package com.baghdad.tudee.data.service.shared

import com.baghdad.tudee.domain.exception.DatabaseCorruptException
import com.baghdad.tudee.domain.exception.DatabaseException
import com.baghdad.tudee.domain.exception.StorageFullException
import com.baghdad.tudee.domain.exception.TudeeException
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DatabaseErrorHandlerTest {

    private val handler = object : DatabaseErrorHandler() {}

    @Test
    fun `should throw DatabaseCorruptException when database is corrup`() = runTest {
        val expectedMessage = "Unknown error"

        val result = runCatching {
            handler.executeWithErrorHandling<Int> {
                throw DatabaseCorruptException(expectedMessage)
            }
        }
        val exception = result.exceptionOrNull()

        if (exception != null) {
            assertEquals(expectedMessage, exception.message)
        }

    }

    @Test
    fun `should throw DatabaseException when unknown error occurs`() = runTest {
        val expectedMessage = "Unknown error"

        val result = runCatching {
            handler.executeWithErrorHandling<Int> {
                throw DatabaseException(expectedMessage)
            }
        }
        val exception = result.exceptionOrNull()

        if (exception != null) {
            assertEquals(expectedMessage, exception.message)
        }
    }

    @Test
    fun `should throw StorageFullException when the storage is full`() = runTest {
        val expectedMessage = "Unknown error"

        val result = runCatching {
            handler.executeWithErrorHandling<Int> {
                throw StorageFullException(expectedMessage)
            }
        }
        val exception = result.exceptionOrNull()

        if (exception != null) {
            assertEquals(expectedMessage, exception.message)
        }
    }

    @Test
    fun `should throw TudeeException with unknown error`() = runTest {
        val expectedMessage = "Unknown error"

        val result = runCatching {
            handler.executeWithErrorHandling<Int> {
                throw TudeeException(expectedMessage)
            }
        }
        val exception = result.exceptionOrNull() as? TudeeException

        if (exception != null) {
            assertEquals(expectedMessage, exception.message)
        }
    }
}