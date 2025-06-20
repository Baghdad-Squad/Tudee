package com.baghdad.tudee.data.service

import com.baghdad.tudee.data.database.dao.AppConfigurationDao
import com.baghdad.tudee.data.service.shared.DatabaseErrorHandler
import com.baghdad.tudee.domain.service.AppConfigurationService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppConfigurationServiceImpl(
    private val appConfigurationDao: AppConfigurationDao
) : DatabaseErrorHandler(), AppConfigurationService {
    override suspend fun isDarkTheme(): Flow<Boolean> = executeWithErrorHandling {
        appConfigurationDao.isDarkTheme().map { it == true }
    }

    override suspend fun setTheme(isDark: Boolean) = executeWithErrorHandling {
        appConfigurationDao.saveTudeeTheme(isDark)
    }

    override suspend fun isFirstLaunch() = executeWithErrorHandling {
        appConfigurationDao
            .isFirstLaunch()
            ?.let { it == true }
            ?: run {
                appConfigurationDao.setIsFirstLaunch()
                true
            }
    }
}