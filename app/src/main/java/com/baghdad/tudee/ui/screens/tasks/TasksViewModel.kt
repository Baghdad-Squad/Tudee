package com.baghdad.tudee.ui.screens.tasks

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
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

open class TasksViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService
) : BaseViewModel<TasksScreenState, Unit>(TasksScreenState()),
    TasksInteractionListener {

    init {
        getCurrentTasks()
        getCategories()
    }


    override fun onTabSelected(selectedTab: Task.State) {
        updateState {
            it.copy(
                selectedTab = selectedTab,
            )
        }
    }

    override fun onDateSelectedFromHorizontalRow(selectedDate: LocalDate) {
        updateState {
            it.copy(
                selectedDate = selectedDate
            )
        }
        loadTasksForDate(selectedDate)
    }

    override fun onDatePickedFromDateDialog(selectedDate: LocalDate) {
        val newMonthDates = getMonthDates(selectedDate)
        updateState {
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
        updateState {
            it.copy(
                taskToDelete = task,
                showDeleteSheet = true
            )
        }
    }

    override fun onPreviousMonthArrowClick() {
        val currentMonthOrdinal = state.value.currentMonth.ordinal
        val newMonthOrdinal = if (currentMonthOrdinal == 0) 11 else currentMonthOrdinal - 1
        val newYear =
            if (currentMonthOrdinal == 0) state.value.currentYear - 1
            else state.value.currentYear
        val newMonth = Month.entries[newMonthOrdinal]

        val newDate = LocalDate(newYear, newMonth, 1)
        val newMonthDates = getMonthDates(newDate)

        updateState {
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
        val currentMonthOrdinal = state.value.currentMonth.ordinal
        val newMonthOrdinal = if (currentMonthOrdinal == 11) 0 else currentMonthOrdinal + 1
        val newYear =
            if (currentMonthOrdinal == 11) state.value.currentYear + 1
            else state.value.currentYear
        val newMonth = Month.entries[newMonthOrdinal]

        val newDate = LocalDate(newYear, newMonth, 1)
        val newMonthDates = getMonthDates(newDate)

        updateState {
            it.copy(
                currentMonth = newMonth,
                currentYear = newYear,
                monthDates = newMonthDates,
                selectedDate = newDate
            )
        }

        loadTasksForDate(newDate)
    }

    override fun toggleAddEditTaskDialog(initialTaskId: Long?) {
        val initialTask = initialTaskId?.let { taskId ->
            getTaskById(taskId)
        } ?: Task(
            date = state.value.selectedDate ?: LocalDate.now(),
            id = 0,
            title = "",
            description = "",
            priority = Task.Priority.LOW,
            categoryId = 0,
            state = Task.State.TODO
        )
        updateState {
            it.copy(
                initialTask = initialTask,
                showAddNewTask = !state.value.showAddNewTask
            )
        }
    }

    private fun getTaskById(taskId: Long): Task? {
        return currentState.todoTasks.find { it.id == taskId }
            ?: currentState.inProgressTasks.find { it.id == taskId }
            ?: currentState.doneTasks.find { it.id == taskId }
    }

    override fun toggleTaskDetailsDialog(selectedTask: Task?) {
        val taskCategory =
            currentState.categories.find { it.id == selectedTask?.categoryId }
        updateState {
            it.copy(
                selectedTaskDetails = selectedTask?.toTaskDetailsState(taskCategory)
                    ?: TaskDetailsState(),
                showTaskDetailsBottomSheet = !currentState.showTaskDetailsBottomSheet
            )
        }
    }

    override fun updateTaskState(
        taskId: Long,
        newState: Task.State
    ) {
        tryToExecute(
            function = { val updatedTask = getTaskById(taskId)!!.copy(state = newState)
                taskService.editTask(updatedTask)
            },
            onSuccess = {
                toggleTaskDetailsDialog()
                loadTasksForDate(currentState.selectedDate ?: LocalDate.now())
            },
            onError = ::onClickSaveTaskError,
            scope = viewModelScope,
            dispatcher = Dispatchers.IO
        )
    }

    override fun onConfirmDelete() {
        tryToExecute(
            function = {
                currentState.taskToDelete?.let { task ->
                    taskService.deleteTask(task.id) }
            },
            onSuccess = {
                updateState {
                    it.copy(
                        taskToDelete = null,
                        showDeleteSheet = false
                    )
                }
                loadTasksForDate(currentState.selectedDate ?: LocalDate.now())
            },
            onError = ::onClickSaveTaskError,
        )
        showSnackbar(
            messageRes = R.string.task_deleted_successfully,
            isSuccess = true
        )
    }

    override fun onCancelDelete() {
        updateState {
            it.copy(
                taskToDelete = null,
                showDeleteSheet = false
            )
        }
    }

    private fun loadTasksForDate(selectedDate: LocalDate) {
        tryToCollect(
            function = { taskService.getTasksByDate(selectedDate) },
            onNewValue = { tasks ->
                updateTasksState(tasks, selectedDate)
            },
            onError = ::onLoadTasksForDateError
        )
    }

    private fun updateTasksState(tasks: List<Task>, selectedDate: LocalDate) {
        val groupedTasksByState = tasks.groupBy { it.state }
        updateState {
            it.copy(
                todoTasks = groupedTasksByState[Task.State.TODO] ?: emptyList(),
                inProgressTasks = groupedTasksByState[Task.State.IN_PROGRESS] ?: emptyList(),
                doneTasks = groupedTasksByState[Task.State.DONE] ?: emptyList(),
                selectedDate = selectedDate
            )
        }
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
                        categories = categories
                    )
                }
            },
            onError = ::onGetCategoriesError
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

    override fun onClickSaveTask(task: Task) {
        try {
            if (task.id != 0L) {
                updateTask(task)
                showSnackbar(
                    messageRes = R.string.edit_task_successfully,
                    isSuccess = true
                )
            } else {
                createTask(task)
                showSnackbar(
                    messageRes = R.string.add_task_successfully,
                    isSuccess = true
                )
            }

        } catch (e: Throwable) {
            onClickSaveTaskError(e)
        }
    }

    private fun onClickSaveTaskError(error: Throwable) {
        showSnackbar(
            messageRes = R.string.an_error_occurred_while_saving_task,
            isSuccess = false
        )
    }

    private fun updateTask(task: Task) {
        tryToExecute(
            function = { taskService.editTask(task) },
            onSuccess = {
                loadTasksForDate(currentState.selectedDate ?: LocalDate.now())
                updateState {
                    it.copy(showAddNewTask = false)
                }
            },
            onError = ::onClickSaveTaskError,
        )
    }

    private fun createTask(task: Task) {
        tryToExecute(
            function = { taskService.createTask(task) },
            onSuccess = {
                loadTasksForDate(currentState.selectedDate ?: LocalDate.now())
                updateState {
                    it.copy(showAddNewTask = false)
                }
            },
            onError = ::onClickSaveTaskError,
        )
    }
}