package com.baghdad.tudee.data.service

import com.baghdad.tudee.domain.service.ThemeService
import kotlinx.coroutines.flow.Flow

class ThemeServiceImpl(): ThemeService {
    override suspend fun isDarkTheme(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun setTheme(isDark: Boolean) {
        TODO("Not yet implemented")
    }
}