package com.baghdad.tudee.ui.main

import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baghdad.tudee.domain.service.AppConfigurationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val appConfigurationService: AppConfigurationService
): ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()
    init {
        fetchIsDarkTheme()
        fetchIsFirstLaunch()
    }

    private fun fetchIsFirstLaunch() {
        viewModelScope.launch (Dispatchers.IO){
            appConfigurationService.isFirstLaunch().let{ isFirstLaunch ->
                _uiState.update {
                    it.copy(isFirstLaunch = isFirstLaunch)
                }
            }
        }
    }

    private fun fetchIsDarkTheme() {
        viewModelScope.launch (Dispatchers.IO){
            appConfigurationService.isDarkTheme().collect { isDarkTheme ->
                _uiState.update {  it.copy(isDarkTheme = isDarkTheme) }
            }
        }
    }
}