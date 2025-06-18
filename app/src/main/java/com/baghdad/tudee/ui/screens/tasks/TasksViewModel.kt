package com.baghdad.tudee.ui.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.domain.service.TaskService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi

class TasksViewModel(
    private val taskService: TaskService
) : ViewModel(), TasksInteractionHandler {
    private val _uiState = MutableStateFlow(TasksUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadTasks()
    }


    override fun onTabSelected(selectedTab: Task.State) {
        _uiState.update {
            it.copy(
                selectedTab = selectedTab
            )
        }
        loadTasks()
    }

    override fun onDateSelected(selectedDate: String) {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun onDeleteTask(task: Task) {
        viewModelScope.launch {
            taskService.deleteTask(task.id)
        }
    }



    private fun loadTasks() {
        viewModelScope.launch {
//            taskService.getTasksByStatus(uiState.value.selectedTab)
//                .collect { data ->
//                    _uiState.update {
//                        it.copy(tasks = data)
//                    }
//                }


//            taskService.getTasksByDate(uiState.value.selectedDate)
//                .collect { data ->
//                    _uiState.update {
//                        it.copy(
//                            tasks = data
//                                .map { task ->
//                                    TaskUi(
//                                        title = task.title,
//                                        description = task.description,
//                                        priority = task.priority,
//                                        icon = task.icon
//                                    )
//                                }
//                        )
//                    }
//                }
        }
    }
}