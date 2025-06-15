package com.baghdad.tudee.domain.service

interface ThemeService {
    suspend fun  getTheme(): Boolean
    suspend fun setTheme(isDark: Boolean)


}