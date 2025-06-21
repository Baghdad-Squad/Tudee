package com.baghdad.tudee.ui.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.domain.service.CategoryService
import com.baghdad.tudee.domain.service.TaskService
import com.baghdad.tudee.ui.utils.now
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class TasksViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService
) : ViewModel(), TasksInteractionListener, AddEditTaskInteractionListener {
    private val _uiState = MutableStateFlow(TasksUiState())
    val uiState = _uiState.asStateFlow()

    private val _taskToDelete = MutableStateFlow<Task?>(null)
    val taskToDelete = _taskToDelete.asStateFlow()

    private val _showDeleteSheet = MutableStateFlow(false)
    val showDeleteSheet = _showDeleteSheet.asStateFlow()

    init {
        getCurrentTasks()
        getCategories()
    }


    override fun onTabSelected(selectedTab: Task.State) {
        _uiState.update {
            it.copy(
                selectedTab = selectedTab,
            )
        }
    }

    override fun onDateSelectedFromHorizontalRow(selectedDate: LocalDate) {
        _uiState.update {
            it.copy(
                selectedDate = selectedDate,
            )
        }
        loadTasksForDate(selectedDate)
    }

    override fun onDatePickedFromDateDialog(selectedDate: LocalDate) {
        val newMonthDates = getMonthDates(selectedDate)

        _uiState.update {
            it.copy(
                selectedDate = selectedDate,
                monthDates = newMonthDates,
                currentMonth = selectedDate.month,
                currentYear = selectedDate.year
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
        val currentMonthOrdinal = uiState.value.currentMonth.ordinal
        val newMonthOrdinal = if (currentMonthOrdinal == 0) 11 else currentMonthOrdinal - 1
        val newYear =
            if (currentMonthOrdinal == 0) uiState.value.currentYear - 1 else uiState.value.currentYear
        val newMonth = Month.entries[newMonthOrdinal]

        val newDate = LocalDate(newYear, newMonth, 1)
        val newMonthDates = getMonthDates(newDate)

        _uiState.update {
            it.copy(
                currentMonth = newMonth,
                currentYear = newYear,
                monthDates = newMonthDates,
                selectedDate = newDate
            )
        }

        loadTasksForDate(newDate)
    }

    override fun onNextMonthArrowClick() {
        val currentMonthOrdinal = uiState.value.currentMonth.ordinal
        val newMonthOrdinal = if (currentMonthOrdinal == 11) 0 else currentMonthOrdinal + 1
        val newYear =
            if (currentMonthOrdinal == 11) uiState.value.currentYear + 1 else uiState.value.currentYear
        val newMonth = Month.entries[newMonthOrdinal]

        val newDate = LocalDate(newYear, newMonth, 1)
        val newMonthDates = getMonthDates(newDate)

        _uiState.update {
            it.copy(
                currentMonth = newMonth,
                currentYear = newYear,
                monthDates = newMonthDates,
                selectedDate = newDate
            )
        }

        loadTasksForDate(newDate)
    }

    override fun toggleAddNewTaskDialog() {
        _uiState.update {
            it.copy(
                showAddNewTask = !_uiState.value.showAddNewTask
            )
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
        viewModelScope.launch {
            taskService.getTasksByDate(selectedDate).collect { tasks ->
                val groupedTasksByState = tasks.groupBy { it.state }
                _uiState.update {
                    it.copy(
                        todoTasks = groupedTasksByState[Task.State.TODO] ?: emptyList(),
                        inProgressTasks = groupedTasksByState[Task.State.IN_PROGRESS] ?: emptyList(),
                        doneTasks = groupedTasksByState[Task.State.DONE] ?: emptyList()
                    )
                }
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            categoryService.getCategories().collect { categories ->
                _uiState.update {
                    it.copy(
                        categories = categories
                    )
                }
            }
        }
    }

    private fun getCurrentTasks() {
        val now = Clock.System.now()
        val today = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
        val initialWeek = getMonthDates(today)

        _uiState.update {
            it.copy(
                selectedDate = today,
                monthDates = initialWeek
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

    override fun onClickAddNewTask(task: Task) {
        viewModelScope.launch {
            taskService.createTask(task)


            loadTasksForDate(uiState.value.selectedDate?: LocalDate.now())

            _uiState.update {
                it.copy(showAddNewTask = false)
            }
        }
    }
}