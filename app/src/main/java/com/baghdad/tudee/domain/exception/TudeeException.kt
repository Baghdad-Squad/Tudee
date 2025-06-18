package com.baghdad.tudee.domain.exception

open class TudeeException(message: String?): Exception(message)

data class DatabaseException(override val message: String?) : TudeeException(message)
data class StorageFullException(override val message: String?) : TudeeException(message)
data class DatabaseCorruptException(override val message: String?) : TudeeException(message)