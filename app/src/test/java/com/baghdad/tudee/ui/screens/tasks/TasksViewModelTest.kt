package com.baghdad.tudee.ui.screens.tasks

import com.baghdad.tudee.data.service.errorHandling.TestDummyData.Companion.sampleTask
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.domain.service.CategoryService
import com.baghdad.tudee.domain.service.TaskService
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class TasksViewModelTest {

    private val taskService: TaskService = mockk(relaxed = true)
    private val categoryService: CategoryService = mockk(relaxed = true)
    private lateinit var viewModel: TasksViewModel

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        coEvery { taskService.getTasksByDate(any()) } returns flowOf(listOf(sampleTask))
        coEvery { categoryService.getCategories() } returns flowOf(emptyList())

        viewModel = TasksViewModel(taskService, categoryService)
        testScope.advanceUntilIdle()
    }

    @Test
    fun `onTabSelected updates selectedTab`() = testScope.runTest {
        viewModel.onTabSelected(Task.State.DONE)
        assertEquals(Task.State.DONE, viewModel.uiState.value.selectedTab)
    }

    @Test
    fun `onDateSelectedFromHorizontalRow updates selectedDate and loads tasks`() = testScope.runTest {
        val date = LocalDate(2024, 6, 15)
        viewModel.onDateSelectedFromHorizontalRow(date)
        assertEquals(date, viewModel.uiState.value.selectedDate)
        assertEquals(listOf(sampleTask), viewModel.uiState.value.todoTasks)
    }

    @Test
    fun `onDatePickedFromDateDialog updates UI state correctly`() = testScope.runTest {
        val date = LocalDate(2024, 5, 1)
        viewModel.onDatePickedFromDateDialog(date)
        assertEquals(date, viewModel.uiState.value.selectedDate)
        assertEquals(date.month, viewModel.uiState.value.currentMonth)
        assertEquals(date.year, viewModel.uiState.value.currentYear)
    }

    @Test
    fun `onPreviousMonthArrowClick updates to previous month`() = testScope.runTest {
        val currentMonth = viewModel.uiState.value.currentMonth
        viewModel.onPreviousMonthArrowClick()
        val expectedMonth = if (currentMonth == Month.JANUARY) Month.DECEMBER else Month.entries[currentMonth.ordinal - 1]
        assertEquals(expectedMonth, viewModel.uiState.value.currentMonth)
    }

    @Test
    fun `onNextMonthArrowClick updates to next month`() = testScope.runTest {
        val currentMonth = viewModel.uiState.value.currentMonth
        viewModel.onNextMonthArrowClick()
        val expectedMonth = if (currentMonth == Month.DECEMBER) Month.JANUARY else Month.entries[currentMonth.ordinal + 1]
        assertEquals(expectedMonth, viewModel.uiState.value.currentMonth)
    }

    @Test
    fun `toggleAddEditTaskDialog creates new task if id null`() = testScope.runTest {
        viewModel.toggleAddEditTaskDialog(null)
        assertTrue(viewModel.uiState.value.showAddNewTask)
        assertEquals("", viewModel.uiState.value.initialTask?.title)
    }

    @Test
    fun `onTaskSwipeToDelete sets taskToDelete and shows sheet`() = testScope.runTest {
        viewModel.onTaskSwipeToDelete(sampleTask)
        assertEquals(sampleTask, viewModel.taskToDelete.value)
        assertTrue(viewModel.showDeleteSheet.value)
    }

    @Test
    fun `cancelDelete clears taskToDelete and hides sheet`() = testScope.runTest {
        viewModel.onTaskSwipeToDelete(sampleTask)
        viewModel.cancelDelete()
        assertNull(viewModel.taskToDelete.value)
        assertFalse(viewModel.showDeleteSheet.value)
    }

    @Test
    fun `loadTasksForDate should update UI state with grouped tasks`() = testScope.runTest {
        val date = LocalDate.parse("2024-06-26")
        viewModel.onDateSelectedFromHorizontalRow(date)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(1, state.todoTasks.size)
        assertEquals("Test Task", state.todoTasks.first().title)
    }


    @Test
    fun `onTabSelected should update selected tab in UI state`() = testScope.runTest {
        viewModel.onTabSelected(Task.State.DONE)

        val state = viewModel.uiState.value
        assertEquals(Task.State.DONE, state.selectedTab)
    }

    @Test
    fun `onDeleteTask should call taskService delete`() = testScope.runTest {
        coEvery { taskService.deleteTask(sampleTask.id) } just Runs

        viewModel.onDeleteTask(sampleTask)
        advanceUntilIdle()

        coVerify { taskService.deleteTask(sampleTask.id) }
    }

}
