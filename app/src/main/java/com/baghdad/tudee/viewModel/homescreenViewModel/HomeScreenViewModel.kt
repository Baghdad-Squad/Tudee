package com.baghdad.tudee.viewModel.homescreenViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.domain.exception.DatabaseCorruptException
import com.baghdad.tudee.domain.exception.DatabaseException
import com.baghdad.tudee.domain.exception.StorageFullException
import com.baghdad.tudee.domain.service.AppConfigurationService
import com.baghdad.tudee.domain.service.CategoryService
import com.baghdad.tudee.domain.service.TaskService
import com.baghdad.tudee.ui.screens.homeScreen.HomeScreenUIState
import com.baghdad.tudee.ui.screens.homeScreen.SliderState
import com.baghdad.tudee.ui.screens.homeScreen.TaskUIState
import com.baghdad.tudee.ui.screens.tasks.AddEditTaskInteractionListener
import com.baghdad.tudee.ui.utils.now
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class HomeScreenViewModel(
    private val appConfigurationService: AppConfigurationService,
    private val taskService: TaskService,
    private val categoryService: CategoryService,
) : HomeScreenInteraction, ViewModel(), AddEditTaskInteractionListener {
    private val _state = MutableStateFlow(HomeScreenUIState())
    val state = _state.asStateFlow()


    init {
        getTasks()
        getCategories()
        getDarkTheme()

    }

    private fun getDarkTheme() {
        viewModelScope.launch (Dispatchers.IO){
            appConfigurationService.isDarkTheme().collect {
                _state.update { currentState ->
                    currentState.copy(isDark = it)
                }
            }
        }
    }

    override fun getTaskDetailsById(id: Long) {
        val task = _state.value.inProgressTasks.find { it.id == id }
            ?: _state.value.todoTasks.find { it.id == id }
            ?: _state.value.doneTasks.find { it.id == id }

        if (task != null) {
            _state.update {
                it.copy(
                    detailsTaskState = it.detailsTaskState.copy(
                        id = task.id,
                        title = task.title,
                        description = task.description,
                        date = task.date,
                        priority = task.priority,
                        categoryId = task.categoryId,
                        state = task.state
                    ),
                    showTaskDetails = true,
                    showAddNewTask = false,
                    showEditTask = false
                )
            }
        }
    }

    override fun onClickAddNewTask(task: Task) {
        viewModelScope.launch {
            try {
                taskService.createTask(task)
                _state.update { currentState ->
                    currentState.copy(
                        inProgressTasks = currentState.inProgressTasks + task,
                        todoTasks = currentState.todoTasks - task,
                        doneTasks = currentState.doneTasks - task
                    )
                }
                showSuccessMessage("Task added successfully")
                _state.update {
                    it.copy(
                        showAddNewTask = false,
                        showEditTask = false,
                        showTaskDetails = false,
                    )
                }

            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    override fun togileEditTaskDialog() {
        _state.update {
            it.copy(
                showEditTask = !_state.value.showEditTask,
            )
        }
    }

    fun toggleAddNewTaskDialog() {
        _state.update {
            it.copy(
                showAddNewTask = !_state.value.showAddNewTask,
                showEditTask = false,
                showTaskDetails = false
            )
        }
    }

    fun toggleTaskDetailsDialog(){
        _state.update {
            it.copy(
                showTaskDetails = !_state.value.showTaskDetails,
                showAddNewTask = false,
                showEditTask = false
            )
        }
    }


    override fun onClickEditTask(task: Task) {
        viewModelScope.launch {
            val taskUiState = _state.value.editTaskState
            try {
                taskService.editTask(taskUiState.toTask())
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }


    override fun onClickSwitchTheme() {
        viewModelScope.launch {
            try {
                appConfigurationService.setTheme(_state.value.isDark.not())
            } catch (error: Exception) {
                handleError(error)
            }
        }
    }

    override fun showTaskDetailsDialog() {
        _state.update {
            it.copy(
                showAddNewTask = false,
                showEditTask = false,
                showTaskDetails = true
            )
        }
    }

    override fun showAddTaskDialog() {
        _state.update {
            it.copy(
                showAddNewTask = true,
                showEditTask = false,
                showTaskDetails = false
            )
        }
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

    private fun getCategories() {
        viewModelScope.launch {
            try {
                categoryService.getCategories().collect { categories ->
                    _state.update { currentState ->
                        currentState.copy(
                            addTaskState = currentState.addTaskState.copy(categories = categories),
                            editTaskState = currentState.editTaskState.copy(categories = categories),
                            detailsTaskState = currentState.detailsTaskState.copy(categories = categories)
                        )
                    }
                }
            } catch (e: Exception) {
                handleError(e)
            }
        }

    }

    private fun getTasks() {
        viewModelScope.launch {
            try {
                val dateNow = LocalDate.now()
                taskService.getTasksByDate(dateNow).collect { it ->
                    val tasksToday = it.groupBy {
                        it.state
                    }
                    Log.d("HomeScreenViewModel", "Tasks grouped by state: $tasksToday fro date: $dateNow")
                    _state.update {
                        it.copy(
                            inProgressTasks = tasksToday[Task.State.IN_PROGRESS] ?: emptyList(),
                            todoTasks = tasksToday[Task.State.TODO] ?: emptyList(),
                            doneTasks = tasksToday[Task.State.DONE] ?: emptyList(),
                        )
                    }
                    controlSliderContent()
                }
            } catch (e: Exception) {
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
            is StorageFullException -> error.message.toString()
            is DatabaseCorruptException -> "Database client info error: ${error.message}"
            is DatabaseException -> error.message.toString()
            else -> "An unexpected error occurred: ${error.message}"
        }

        _state.update { it.copy(errorMessage = errorMessage) }
        showSnackbarMessage(errorMessage, isError = true)
        delay(5000)
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

fun TaskUIState.toTask() =
    Task(
        id = this.id,
        title = this.title,
        description = this.description,
        date = this.date,
        priority = this.priority,
        categoryId = this.categoryId,
        state = this.state
    )