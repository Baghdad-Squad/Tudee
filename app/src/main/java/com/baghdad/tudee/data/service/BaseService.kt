package com.baghdad.tudee.data.service

import android.database.sqlite.SQLiteDatabaseCorruptException
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteFullException
import com.baghdad.tudee.domain.exception.DatabaseCorruptException
import com.baghdad.tudee.domain.exception.DatabaseException
import com.baghdad.tudee.domain.exception.StorageFullException
import com.baghdad.tudee.domain.exception.TudeeException

abstract class BaseService {
    suspend fun<T> executeWithErrorHandling(block: suspend () -> T): T{
        return try {
            block()
        } catch (e: SQLiteFullException) {
            throw StorageFullException(e.message)
        } catch (e: SQLiteDatabaseCorruptException) {
            throw DatabaseCorruptException(e.message)
        } catch (e: SQLiteException) {
            throw DatabaseException(e.message)
        } catch (e: Exception) {
            throw TudeeException(e.message)
        }
    }
}