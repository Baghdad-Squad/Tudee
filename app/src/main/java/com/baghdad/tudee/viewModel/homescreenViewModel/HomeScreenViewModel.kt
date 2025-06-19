package com.baghdad.tudee.viewModel.homescreenViewModel

import android.database.sqlite.SQLiteDatabaseCorruptException
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteFullException
import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.domain.service.AppConfigurationService
import com.baghdad.tudee.domain.service.TaskService
import com.baghdad.tudee.ui.screens.homeScreen.HomeScreenUIState
import com.baghdad.tudee.ui.screens.homeScreen.SliderState
import com.baghdad.tudee.ui.utils.now
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class HomeScreenViewModel(
    private val appConfigurationService: AppConfigurationService,
    private val taskService: TaskService,
) : HomeScreenInteraction, ViewModel() {
    private val _state = MutableStateFlow(HomeScreenUIState())
    val state = _state.asStateFlow()


    init {
        getTasks()
    }

    override fun onClickAddNewTask() {
        _state.update { it.copy(addNewTask = true, editTask = false, taskDetails = false) }
    }

    override fun onClickTask(taskId: Long) {
        _state.update { it.copy(addNewTask = false, editTask = false, taskDetails = true) }
    }

    override fun onClickSwitchTheme() {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isDark = !_state.value.isDark) }
                appConfigurationService.setTheme(_state.value.isDark)
            } catch (error: Exception) {
                handleError(error)
            }
        }
    }

    override fun onClickEditTask(taskId: Long) {
        _state.update { it.copy(addNewTask = false, editTask = true, taskDetails = false) }
    }

    override fun showTaskDetails(taskId: Long) {
        _state.update { it.copy(addNewTask = false, editTask = false, taskDetails = true) }
    }

    override fun moveTaskToDone(taskId: Long) {
        viewModelScope.launch {
            try {
                taskService.getTasksByCategory(taskId)
                    .collect { tasks ->
                        tasks.find { it.id == taskId }?.let { task ->
                            val updatedTask = task.copy(state = Task.State.DONE)
                            taskService.editTask(updatedTask)
                            _state.update { currentState ->
                                currentState.copy(
                                    inProgressTasks = _state.value.inProgressTasks - task,
                                    doneTasks = _state.value.doneTasks + updatedTask,
                                    todoTasks = _state.value.todoTasks - task,
                                )
                            }
                        } ?: run {
                            _state.update { it.copy(errorMessage = "Task not found") }
                        }
                    }
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = "Failed to update task: ${e.message}") }
                _state.value.errorMessage?.let {
                    handleError(e)
                }
            }
        }
    }


    override fun moveTaskToTodo(taskId: Long) {
        viewModelScope.launch {
            try {
                taskService.getTasksByCategory(taskId)
                    .collect { tasks ->
                        tasks.find { it.id == taskId }?.let { task ->
                            val updatedTask = task.copy(state = Task.State.TODO)
                            taskService.editTask(updatedTask)
                            _state.update { currentState ->
                                currentState.copy(
                                    inProgressTasks = _state.value.inProgressTasks - task,
                                    doneTasks = _state.value.doneTasks - updatedTask,
                                    todoTasks = _state.value.todoTasks + task,
                                )
                            }
                        } ?: run {
                            _state.update { it.copy(errorMessage = "Task not found") }
                        }
                    }
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = "Failed to update task: ${e.message}") }
                _state.value.errorMessage?.let {
                    handleError(e)
                }
            }
        }
    }

    override fun moveTaskToInProgress(taskId: Long) {
        viewModelScope.launch {
            try {
                taskService.getTasksByCategory(taskId)
                    .collect { tasks ->
                        tasks.find { it.id == taskId }?.let { task ->
                            val updatedTask = task.copy(state = Task.State.IN_PROGRESS)
                            taskService.editTask(updatedTask)
                            _state.update { currentState ->
                                currentState.copy(
                                    inProgressTasks = _state.value.inProgressTasks + task,
                                    doneTasks = _state.value.doneTasks - updatedTask,
                                    todoTasks = _state.value.todoTasks - task,
                                )
                            }
                        } ?: run {
                            _state.update { it.copy(errorMessage = "Task not found") }
                        }
                    }
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = "Failed to update task: ${e.message}") }
                _state.value.errorMessage?.let {
                    handleError(e)
                }
            }
        }
    }

    override fun showSnarkMessage(message: String, isVisible: Boolean, isError: Boolean) {
        _state.update {
            it.copy(
                showSnackBar = _state.value.showSnackBar.copy(
                    message = message,
                    isVisible = isVisible,
                    isError = isError
                )
            )
        }
    }




    private fun getTasks() {
        viewModelScope.launch {

            try {
                taskService.getTasksByDate(LocalDate.now()).collect { it ->
                    val tasksToday = it.groupBy {
                        it.state
                    }

                    _state.update { it.copy(
                            inProgressTasks = tasksToday[Task.State.IN_PROGRESS] ?: emptyList(),
                            todoTasks = tasksToday[Task.State.TODO] ?: emptyList(),
                            doneTasks = tasksToday[Task.State.DONE] ?: emptyList(),
                        ) }
                    controlSliderContent()
                }
            }catch (e: Exception){
                handleError(e)
            }
        }
    }

    private fun controlSliderContent() {
        when {
            state.value.inProgressTasks.isEmpty() &&
                    state.value.todoTasks.isEmpty() &&
                    state.value.doneTasks.isEmpty() -> _state.update {
                it.copy(
                    sliderState = SliderState.NOTHING_IN_YOUR_LIST
                )
            }
            state.value.inProgressTasks.isEmpty() &&
                    state.value.todoTasks.isEmpty() &&
                    state.value.doneTasks.isNotEmpty() -> _state.update {
                it.copy(
                    sliderState = SliderState.TADOO
                )
            }
            state.value.doneTasks.isNotEmpty()
                    && state.value.inProgressTasks.isNotEmpty()
                    && state.value.todoTasks.isNotEmpty() -> _state.update {
                it.copy(
                    sliderState = SliderState.STAY_WORKING
                )
            }
            state.value.todoTasks.isNotEmpty() &&
                    state.value.inProgressTasks.isEmpty() &&
                    state.value.doneTasks.isEmpty() -> _state.update {
                it.copy(
                    sliderState = SliderState.ZERO_PROGRESS
                )
            }
        }

    }
    private suspend fun handleError(error: Exception) {
        val errorMessage = when (error) {
            is SQLiteFullException -> error.message.toString()
            is SQLiteDatabaseCorruptException -> error.message.toString()
            is SQLiteException -> "Database client info error: ${error.message}"
            else -> "An unexpected error occurred: ${error.message}"
        }

        _state.update { it.copy(errorMessage = errorMessage) }
        showSnackbarMessage(errorMessage, isError = true)
        delay(10000)
        hideSnackbarMessage()
    }

    private suspend fun showSuccessMessage(message: String) {
        showSnackbarMessage(
            message = message,
            isError = false
        )
        delay(3000)
        hideSnackbarMessage()
    }

    private fun showSnackbarMessage(
        message: String,
        isError: Boolean,
    ) {
        showSnarkMessage(
            message = message,
            isVisible = true,
            isError = isError
        )
    }

    private fun hideSnackbarMessage() {
        showSnarkMessage(
            message = "",
            isVisible = false,
            isError = false
        )
    }
}
