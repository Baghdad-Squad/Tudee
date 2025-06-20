package com.baghdad.tudee.domain.service

import kotlinx.coroutines.flow.Flow

interface AppConfigurationService {
    suspend fun isDarkTheme(): Flow<Boolean>
    suspend fun setTheme(isDark: Boolean)
    suspend fun isFirstLaunch(): Boolean
}