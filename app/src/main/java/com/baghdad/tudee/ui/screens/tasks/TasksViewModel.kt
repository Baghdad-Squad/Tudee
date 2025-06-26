package com.baghdad.tudee.ui.screens.tasks

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.baghdad.tudee.R
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.domain.service.CategoryService
import com.baghdad.tudee.domain.service.TaskService
import com.baghdad.tudee.ui.base.BaseViewModel
import com.baghdad.tudee.ui.screens.homeScreen.TaskDetailsState
import com.baghdad.tudee.ui.screens.homeScreen.toTaskDetailsState
import com.baghdad.tudee.ui.utils.now
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class TasksViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService
) : TasksInteractionListener, AddEditTaskInteractionListener,
    BaseViewModel<TasksScreenState, TasksScreenEffect>(TasksScreenState()) {


    private val _taskToDelete = MutableStateFlow<Task?>(null)
    val taskToDelete = _taskToDelete.asStateFlow()

    private val _showDeleteSheet = MutableStateFlow(false)
    val showDeleteSheet = _showDeleteSheet.asStateFlow()

    init {
        getCurrentTasks()
        getCategories()
    }


    override fun onTabSelected(selectedTab: Task.State) {
        updateState {
            it.copy(
                tasksUiState = it.tasksUiState.copy(
                    selectedTab = selectedTab
                )
            )
        }
    }

    override fun onDateSelectedFromHorizontalRow(selectedDate: LocalDate) {
        updateState {
            it.copy(
                tasksUiState = it.tasksUiState.copy(
                    selectedDate = selectedDate
                ),
            )
        }
        loadTasksForDate(selectedDate)
    }

    override fun onDatePickedFromDateDialog(selectedDate: LocalDate) {
        val newMonthDates = getMonthDates(selectedDate)
        updateState {
            it.copy(
                tasksUiState = it.tasksUiState.copy(
                    selectedDate = selectedDate,
                    monthDates = newMonthDates,
                    currentMonth = selectedDate.month,
                    currentYear = selectedDate.year
                )
            )
        }
        loadTasksForDate(selectedDate)
    }


    override fun onDeleteTask(task: Task) {
        viewModelScope.launch {
            taskService.deleteTask(task.id)
        }
    }

    override fun onPreviousMonthArrowClick() {
        val currentMonthOrdinal = state.value.tasksUiState.currentMonth.ordinal
        val newMonthOrdinal = if (currentMonthOrdinal == 0) 11 else currentMonthOrdinal - 1
        val newYear =
            if (currentMonthOrdinal == 0) state.value.tasksUiState.currentYear - 1
            else state.value.tasksUiState.currentYear
        val newMonth = Month.entries[newMonthOrdinal]

        val newDate = LocalDate(newYear, newMonth, 1)
        val newMonthDates = getMonthDates(newDate)

        updateState {
            it.copy(
                tasksUiState = it.tasksUiState.copy(
                    currentMonth = newMonth,
                    currentYear = newYear,
                    monthDates = newMonthDates,
                    selectedDate = newDate
                )
            )
        }

        loadTasksForDate(newDate)
    }

    override fun onNextMonthArrowClick() {
        val currentMonthOrdinal = state.value.tasksUiState.currentMonth.ordinal
        val newMonthOrdinal = if (currentMonthOrdinal == 11) 0 else currentMonthOrdinal + 1
        val newYear =
            if (currentMonthOrdinal == 11) state.value.tasksUiState.currentYear + 1
            else state.value.tasksUiState.currentYear
        val newMonth = Month.entries[newMonthOrdinal]

        val newDate = LocalDate(newYear, newMonth, 1)
        val newMonthDates = getMonthDates(newDate)

        updateState {
            it.copy(
                tasksUiState = it.tasksUiState.copy(
                    currentMonth = newMonth,
                    currentYear = newYear,
                    monthDates = newMonthDates,
                    selectedDate = newDate
                )
            )
        }

        loadTasksForDate(newDate)
    }

    override fun toggleAddEditTaskDialog(initialTaskId: Long?) {
        val initialTask = initialTaskId?.let { taskId ->
            getTaskById(taskId)
        } ?: Task(
            date = state.value.tasksUiState.selectedDate ?: LocalDate.now(),
            id = 0,
            title = "",
            description = "",
            priority = Task.Priority.LOW,
            categoryId = 0,
            state = Task.State.TODO
        )
        updateState {
            it.copy(
                it.tasksUiState.copy(
                    initialTask = initialTask,
                    showAddNewTask = !state.value.tasksUiState.showAddNewTask
                )
            )
        }
    }

    private fun getTaskById(taskId: Long): Task? {
        return currentState.tasksUiState.todoTasks.find { it.id == taskId }
            ?: currentState.tasksUiState.inProgressTasks.find { it.id == taskId }
            ?: currentState.tasksUiState.doneTasks.find { it.id == taskId }
    }

    override fun toggleTaskDetailsDialog(selectedTask: Task?) {
        val taskCategory =
            currentState.tasksUiState.categories.find { it.id == selectedTask?.categoryId }
        updateState {
            it.copy(
                it.tasksUiState.copy(
                    selectedTaskDetails = selectedTask?.toTaskDetailsState(taskCategory)
                        ?: TaskDetailsState(),
                    showTaskDetailsBottomSheet = !currentState.tasksUiState.showTaskDetailsBottomSheet
                )
            )
        }
    }

    override fun updateTaskState(
        taskId: Long,
        newState: Task.State
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedTask = getTaskById(taskId)?.copy(state = newState) ?: return@launch
            taskService.editTask(updatedTask)
            toggleTaskDetailsDialog()
        }
    }

    fun onTaskSwipeToDelete(task: Task) {
        _taskToDelete.value = task
        _showDeleteSheet.value = true
    }

    fun confirmDelete() {
        _taskToDelete.value?.let { task ->
            onDeleteTask(task)
        }
        _taskToDelete.value = null
        _showDeleteSheet.value = false
    }

    fun cancelDelete() {
        _taskToDelete.value = null
        _showDeleteSheet.value = false
    }

    private fun loadTasksForDate(selectedDate: LocalDate) {
        tryToCollect(
            function = { taskService.getTasksByDate(selectedDate) },
            onNewValue = { tasks ->
                val groupedTasksByState = tasks.groupBy { it.state }
                updateState {
                    it.copy(
                        tasksUiState = it.tasksUiState.copy(
                            todoTasks = groupedTasksByState[Task.State.TODO] ?: emptyList(),
                            inProgressTasks = groupedTasksByState[Task.State.IN_PROGRESS]
                                ?: emptyList(),
                            doneTasks = groupedTasksByState[Task.State.DONE] ?: emptyList(),
                            selectedDate = selectedDate
                        )
                    )
                }
            },
            onError = ::onLoadTasksForDateError
        )
    }
    private fun onLoadTasksForDateError(error: Throwable) {
        showSnackbar(
            messageRes = R.string.an_error_occurred_while_loading_tasks,
            isSuccess = false
        )
    }

    private fun getCategories() {
        tryToCollect(
            function = { categoryService.getCategories() },
            onNewValue = { categories ->
                updateState {
                    it.copy(
                        tasksUiState = it.tasksUiState.copy(
                            categories = categories
                        )
                    )
                }
            },
            onError = :: onGetCategoriesError
        )
    }
    private fun onGetCategoriesError(error: Throwable) {
        showSnackbar(
            messageRes = R.string.an_error_occurred_while_fetching_categories,
            isSuccess = false
        )
    }

    private fun getCurrentTasks() {
        val now = Clock.System.now()
        val today = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
        val initialWeek = getMonthDates(today)

        updateState {
            it.copy(
                it.tasksUiState.copy(
                selectedDate = today,
                monthDates = initialWeek
            )
            )
        }

        loadTasksForDate(today)
    }

    private fun getMonthDates(startDate: LocalDate): List<LocalDate> {
        val year = startDate.year
        val month = startDate.month

        val daysInMonth = when (month) {
            Month.JANUARY, Month.MARCH, Month.MAY, Month.JULY,
            Month.AUGUST, Month.OCTOBER, Month.DECEMBER -> 31

            Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30

            Month.FEBRUARY -> if (year.isLeapYear()) 29 else 28
        }
        return (1..daysInMonth).map { day ->
            LocalDate(year, month, day)
        }
    }

    fun Int.isLeapYear(): Boolean {
        return (this % 4 == 0 && this % 100 != 0) || (this % 400 == 0)
    }

    override fun onClickSaveTask(task: Task) {
        Log.d("TasksViewModel", "onClickSaveTask: $task")
        if (task.id != 0L) {
            updateTask(task)
        } else {
            createTask(task)
        }
    }

    private fun updateTask(task: Task) {
        viewModelScope.launch {
            taskService.editTask(task)
            loadTasksForDate(currentState.tasksUiState.selectedDate ?: LocalDate.now())
            updateState {
                it.copy(
                    it.tasksUiState.copy(showAddNewTask = false)
                )
            }
        }
    }

    private fun createTask(task: Task) {
        viewModelScope.launch {
            taskService.createTask(task)
            loadTasksForDate(currentState.tasksUiState.selectedDate ?: LocalDate.now())

            updateState {
                it.copy(
                    it.tasksUiState.copy(showAddNewTask = false)
                )
            }
        }
    }
}