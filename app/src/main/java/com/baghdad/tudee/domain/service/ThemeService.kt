package com.baghdad.tudee.domain.service

import kotlinx.coroutines.flow.Flow

interface ThemeService {
    suspend fun isDarkTheme(): Flow<Boolean>
    suspend fun setTheme(isDark: Boolean)


}