package com.baghdad.tudee.ui.screens.homeScreen

import androidx.lifecycle.viewModelScope
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.domain.exception.DatabaseCorruptException
import com.baghdad.tudee.domain.exception.DatabaseException
import com.baghdad.tudee.domain.exception.StorageFullException
import com.baghdad.tudee.domain.service.AppConfigurationService
import com.baghdad.tudee.domain.service.CategoryService
import com.baghdad.tudee.domain.service.TaskService
import com.baghdad.tudee.ui.base.BaseViewModel
import com.baghdad.tudee.ui.utils.now
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class HomeScreenViewModel(
    private val appConfigurationService: AppConfigurationService,
    private val taskService: TaskService,
    private val categoryService: CategoryService,
) : HomeScreenInteraction, BaseViewModel<HomeScreenUIState, HomeScreenEffect>(HomeScreenUIState()){

    init {
        getTasks()
        getCategories()
        getDarkTheme()
    }

    private fun getDarkTheme() {
        viewModelScope.launch(Dispatchers.IO) {
            appConfigurationService.isDarkTheme().collect {
                updateState { currentState -> currentState.copy(isDark = it) }
            }
        }
    }

    override fun getTaskDetailsById(id: Long) {
        val task = state.value.inProgressTasks.find { it.id == id }
            ?: state.value.todoTasks.find { it.id == id }
            ?: state.value.doneTasks.find { it.id == id }

        if (task != null) {
            updateState {
                it.copy(
                    taskDetailsState = TaskDetailsState(
                        id = task.id,
                        title = task.title,
                        description = task.description,
                        taskState = task.state,
                        taskPriority = task.priority,
                        category = it.categories.firstOrNull { category -> category.id == task.categoryId }
                    ),
                    showTaskDetails = true,
                    showAddNewTask = false,
                    showEditTask = false
                )
            }
        }
    }

     fun onClickSaveTask(task: Task) {
        if (task.id != 0L) {
            updateTask(task)
        } else {
            createTask(task)
        }
    }

    private fun updateTask(task: Task) {
        tryToExecute(
            function = { taskService.editTask(task) },
            onSuccess = {
                loadTasksForDate(state.value.selectedDate ?: LocalDate.now())
                updateState {
                    it.copy(showAddNewTask = false)
                }
                showSuccessMessage("Task updated successfully")
            },
            onError = { error -> handleError(error) }
        )

    }

    private fun createTask(task: Task) {
        tryToExecute(
            function = { taskService.createTask(task) },
            onSuccess = {
                loadTasksForDate(state.value.selectedDate ?: LocalDate.now())
                updateState { it.copy(showAddNewTask = false) }
                showSuccessMessage("Task created successfully")

            },
            onError = { handleError(error = it) },
        )
    }

    private fun loadTasksForDate(selectedDate: LocalDate) {
        tryToCollect(
            function = { taskService.getTasksByDate(selectedDate) },
            onNewValue = { tasks ->
                val groupedTasksByState = tasks.groupBy { it.state }
                updateState {
                    it.copy(
                        todoTasks = groupedTasksByState[Task.State.TODO] ?: emptyList(),
                        inProgressTasks = groupedTasksByState[Task.State.IN_PROGRESS]
                            ?: emptyList(),
                        doneTasks = groupedTasksByState[Task.State.DONE] ?: emptyList()
                    )
                }
            },
            onError = { error ->
                handleError(error)
            }
        )
    }


    override fun togileEditTaskDialog(initialTaskId: Long?) {
        tryToExecute(
            function = {
                initialTaskId?.let { taskId ->
                    state.value.inProgressTasks.find { it.id == taskId }
                        ?: state.value.todoTasks.find { it.id == taskId }
                        ?: state.value.doneTasks.find { it.id == taskId }
                }
            },
            onSuccess = { initialTask ->
                updateState {
                    it.copy(
                        showEditTask = !currentState.showEditTask,
                        editTaskState = it.editTaskState.copy(currentTask = initialTask)
                    )
                }
            },
            onError = { error -> handleError(error) }
        )


    }

    fun toggleAddNewTaskDialog() {
        updateState {
            it.copy(
                showAddNewTask = !currentState.showAddNewTask,
                showEditTask = false,
                showTaskDetails = false
            )
        }
    }

    fun toggleTaskDetailsDialog() {
        updateState {
            it.copy(
                showTaskDetails = !currentState.showTaskDetails,
                showAddNewTask = false,
                showEditTask = false
            )
        }
    }

    override fun onClickEditTask(task: Task) {
        tryToExecute(
            function = {
                val taskUiState = currentState.editTaskState
                taskService.editTask(taskUiState.toTask())

                       },
            onSuccess = {showSuccessMessage("Task updated successfully")},
            onError = { error -> handleError(error) }
        )
        viewModelScope.launch {
            val taskUiState = currentState.editTaskState
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
                appConfigurationService.setTheme(state.value.isDark.not())
            } catch (error: Exception) {
                handleError(error)
            }
        }
    }

    override fun showTaskDetailsDialog() {
        updateState {
            it.copy(
                showAddNewTask = false,
                showEditTask = false,
                showTaskDetails = true
            )
        }
    }

    override fun showAddTaskDialog() {
        updateState {
            it.copy(
                showAddNewTask = true,
                showEditTask = false,
                showTaskDetails = false
            )
        }
    }

    override fun moveTaskToDone(taskId: Long) {
        tryToExecute(
            function = {
                val task = currentState.inProgressTasks.find { it.id == taskId }
                    ?: state.value.todoTasks.find { it.id == taskId }

                if (task != null) {
                    val updatedTask = task.copy(state = Task.State.DONE)
                    taskService.editTask(updatedTask)
                    updatedTask
                } else {
                    updateState { it.copy(errorMessage = "Task not found") }
                    null
                }
            },
            onSuccess = { updatedTask ->
                if (updatedTask != null) {
                    updateState { currentState ->
                        currentState.copy(
                            inProgressTasks = currentState.inProgressTasks - updatedTask,
                            todoTasks = currentState.todoTasks - updatedTask,
                            doneTasks = currentState.doneTasks + updatedTask,
                            errorMessage = null
                        )
                    }
                } else {
                    updateState { it.copy(errorMessage = "Task not found") }
                }
            },
            onError = { e ->
                updateState { it.copy(errorMessage = "Failed to update task: ${e.message}") }
                currentState.errorMessage?.let { handleError(e) }
            },
            dispatcher = Dispatchers.IO
        )
    }

    override fun moveTaskToTodo(taskId: Long) {
        tryToExecute(
            function = {
                currentState.inProgressTasks.find { it.id == taskId }?.let { task ->
                    val updatedTask = task.copy(state = Task.State.TODO)
                    taskService.editTask(updatedTask)
                    updatedTask
                }
            },
            onSuccess = { updatedTask ->
                if (updatedTask != null) {
                    updateState { currentState ->
                        currentState.copy(
                            inProgressTasks = currentState.inProgressTasks - updatedTask,
                            todoTasks = currentState.todoTasks + updatedTask,
                            errorMessage = null
                        )
                    }
                } else {
                    updateState { it.copy(errorMessage = "Task not found") }
                }
            },
            onError = { e ->
                updateState { it.copy(errorMessage = "Failed to update task: ${e.message}") }
                currentState.errorMessage?.let { handleError(e) }
            },
            dispatcher = Dispatchers.IO
        )
    }

    override fun moveTaskToInProgress(taskId: Long) {
        tryToExecute(
            function = {
                currentState.todoTasks.find { it.id == taskId }?.let { task ->
                    val updatedTask = task.copy(state = Task.State.IN_PROGRESS)
                    taskService.editTask(updatedTask)
                    updatedTask
                }
            },
            onSuccess = { updatedTask ->
                if (updatedTask != null) {
                    updateState { currentState ->
                        currentState.copy(
                            todoTasks = currentState.todoTasks - updatedTask,
                            inProgressTasks = currentState.inProgressTasks + updatedTask,
                            errorMessage = null
                        )
                    }
                } else {
                    updateState { it.copy(errorMessage = "Task not found") }
                }
            },
            onError = { e ->
                updateState { it.copy(errorMessage = "Failed to update task: ${e.message}") }
                currentState.errorMessage?.let { handleError(e) }
            },
            dispatcher = Dispatchers.IO
        )
    }

    private fun getCategories() {
        tryToCollect(
            function = { categoryService.getCategories() },
            onNewValue = { categories ->
                updateState { currentState ->
                    currentState.copy(
                        categories = categories,
                        addTaskState = currentState.addTaskState.copy(categories = categories),
                        editTaskState = currentState.editTaskState.copy(categories = categories),
                        detailsTaskState = currentState.detailsTaskState.copy(categories = categories)
                    )
                }
            },
            onError = { e -> handleError(e) }
        )
    }

    private fun getTasks() {
        viewModelScope.launch {
            try {
                val dateNow = LocalDate.now()
                taskService.getTasksByDate(dateNow).collect { it ->
                    val tasksToday = it.groupBy {
                        it.state
                    }
                    updateState {
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

    override fun showSnackbarMessage(message: String, isVisible: Boolean, isError: Boolean) {
        updateState {
            it.copy(
                showSnackBar = currentState.showSnackBar.copy(
                    message = message,
                    isVisible = isVisible,
                    isError = isError
                )
            )
        }
    }

    private fun controlSliderContent() {
        when {
            state.value.inProgressTasks.isEmpty() &&
                    state.value.todoTasks.isEmpty() &&
                    state.value.doneTasks.isEmpty() -> updateState {
                it.copy(
                    sliderState = SliderState.NOTHING_IN_YOUR_LIST
                )
            }

            state.value.inProgressTasks.isEmpty() &&
                    state.value.todoTasks.isEmpty() &&
                    state.value.doneTasks.isNotEmpty() -> updateState {
                it.copy(
                    sliderState = SliderState.TADOO
                )
            }

            state.value.doneTasks.isNotEmpty()
                    && state.value.inProgressTasks.isNotEmpty()
                    && state.value.todoTasks.isNotEmpty() -> updateState {
                it.copy(
                    sliderState = SliderState.STAY_WORKING
                )
            }

            state.value.todoTasks.isNotEmpty() &&
                    state.value.inProgressTasks.isEmpty() &&
                    state.value.doneTasks.isEmpty() -> updateState {
                it.copy(
                    sliderState = SliderState.ZERO_PROGRESS
                )
            }
        }

    }

    private fun handleError(error: Throwable) {
        val errorMessage = when (error) {
            is StorageFullException -> error.message.toString()
            is DatabaseCorruptException -> "Database client info error: ${error.message}"
            is DatabaseException -> error.message.toString()
            else -> "An unexpected error occurred: ${error.message}"
        }
        viewModelScope.launch {

            updateState { it.copy(errorMessage = errorMessage) }
            showSnackbarMessage(errorMessage, isError = true)
            delay(5000)

            hideSnackbarMessage()
        }
    }

    private fun showSuccessMessage(message: String) {
        viewModelScope.launch {
            showSnackbarMessage(
                message = message,
                isError = false
            )
            delay(3000)
            hideSnackbarMessage()
        }
    }

    private fun showSnackbarMessage(
        message: String,
        isError: Boolean,
    ) {
        showSnackbarMessage(
            message = message,
            isVisible = true,
            isError = isError
        )
    }

    private fun hideSnackbarMessage() {
        showSnackbarMessage(
            message = "",
            isVisible = false,
            isError = false
        )
    }
}

