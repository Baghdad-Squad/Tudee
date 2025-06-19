package com.baghdad.tudee.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baghdad.tudee.domain.service.CategoryService
import com.baghdad.tudee.domain.service.TaskService
import com.baghdad.tudee.ui.viewModel.mapper.toEntity
import com.baghdad.tudee.ui.viewModel.mapper.toUiState
import com.baghdad.tudee.ui.viewModel.state.CategoryUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


class CategoryViewModel(
    private val categoryService: CategoryService,
    private val taskService: TaskService
) : ViewModel() {


    @OptIn(ExperimentalUuidApi::class)
    private val _statusValue = MutableStateFlow(
        listOf<CategoryUiState>()
    )
    val statusValue = _statusValue.asStateFlow()

    init {
        getCategories()
    }

    @OptIn(ExperimentalUuidApi::class)
    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            categoryService.getCategories().collect { categories ->
                categories.map {
                    it.toUiState().copy(taskCount = 0

                    )
//                        .copy(taskCount = getTaskCount(it.id))
                }.let { state ->
                    _statusValue.update {
                        state
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun deleteCategory(categoryId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            categoryService.deleteCategory(categoryId)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun addCategory(category: CategoryUiState) {
        viewModelScope.launch(Dispatchers.IO) {
            categoryService.createCategory(category.toEntity())
        }
    }

    fun editCategory(category: CategoryUiState) {
        viewModelScope.launch(Dispatchers.IO) {
            categoryService.editCategory(category.toEntity())
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private suspend fun getTaskCount(id: Long): Int {
        return taskService.getTasksByCategory(id)
            .map { it.count() }
            .first()
    }
}