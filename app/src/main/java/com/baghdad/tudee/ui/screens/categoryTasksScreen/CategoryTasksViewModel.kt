package com.baghdad.tudee.ui.screens.categoryTasksScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.domain.service.CategoryService
import com.baghdad.tudee.domain.service.TaskService
import com.baghdad.tudee.ui.utils.getCategoryIconPainter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryTasksViewModel(
    private val categoryId: Long,
    val taskService: TaskService,
    val categoryService: CategoryService,
) : ViewModel() {
    private val _state = MutableStateFlow(CategoryTasksScreenUiState())
    val state = _state.asStateFlow()

    init {
        getCategoryById()
        getTasksByCategoryId(categoryId)
    }

    fun onTabSelected(tab: Task.State) {
        _state.update {
            it.copy(selectedTab = tab)
        }
    }

    fun getCategoryById() {
        viewModelScope.launch {
            val category = categoryService.getCategoryById(categoryId)
                _state.update {
                    it.copy(
                        categoryName = category.title,
                        categoryImage = category.image,
                        isPredefinedCategory = category.isPredefinedCategory
                    )
                }
        }


    }

    fun getTasksByCategoryId(categoryId: Long) {
        viewModelScope.launch {
            taskService.getTasksByCategory(categoryId).collect { tasks ->
                _state.update {
                    it.copy(
                        todoTasks = tasks.filter { task -> task.state == Task.State.TODO },
                        inProgressTasks = tasks.filter { task -> task.state == Task.State.IN_PROGRESS },
                        doneTasks = tasks.filter { task -> task.state == Task.State.DONE }
                    )
                }

            }

        }
    }

    fun onCategoryTitleChanged(newTitle: String) {
        _state.update { it.copy(categoryName = newTitle) }
    }

    fun onDeleteCategory() {
        viewModelScope.launch {
            categoryService.deleteCategory(categoryId)
        }
    }

//    fun onSaveCategoryChanges() {
//        viewModelScope.launch {
//            categoryService.editCategory(
//            )
//        }
//    }
}