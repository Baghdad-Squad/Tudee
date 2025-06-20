package com.baghdad.tudee.ui.screens.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baghdad.tudee.domain.service.CategoryService
import com.baghdad.tudee.domain.service.TaskService
import com.baghdad.tudee.ui.screens.category.mapper.toEntity
import com.baghdad.tudee.ui.screens.category.mapper.toUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryService: CategoryService,
    private val taskService: TaskService
) : ViewModel() {

    private val _statusValue = MutableStateFlow(
        listOf<CategoryUiState>()
    )
    val statusValue = _statusValue.asStateFlow()

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            categoryService.getCategories().collect { categories ->
                categories.map {
                    it.toUiState().copy(taskCount = getTaskCount(it.id))
                }.let { state ->
                    _statusValue.update {
                        state
                    }
                }
            }
        }
    }

    fun addCategory(category: CategoryUiState) {
        viewModelScope.launch(Dispatchers.IO) {
            categoryService.createCategory(category.toEntity())
        }
    }


    private suspend fun getTaskCount(id: Long): Int {
        return taskService.getTasksByCategory(id)
            .map { it.count() }
            .first()
    }
}