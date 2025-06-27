package com.baghdad.tudee.ui.screens.tasks

import com.baghdad.tudee.TestDummyData.Companion.day
import com.baghdad.tudee.TestDummyData.Companion.day10
import com.baghdad.tudee.TestDummyData.Companion.day5
import com.baghdad.tudee.TestDummyData.Companion.dayOfMonth
import com.baghdad.tudee.TestDummyData.Companion.expectedDaysInFeb
import com.baghdad.tudee.TestDummyData.Companion.expectedDaysInMonth
import com.baghdad.tudee.TestDummyData.Companion.id
import com.baghdad.tudee.TestDummyData.Companion.month
import com.baghdad.tudee.TestDummyData.Companion.month2
import com.baghdad.tudee.TestDummyData.Companion.sampleCategory
import com.baghdad.tudee.TestDummyData.Companion.sampleTask
import com.baghdad.tudee.TestDummyData.Companion.year
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.domain.service.CategoryService
import com.baghdad.tudee.domain.service.TaskService
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TasksViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val taskService = mockk<TaskService>(relaxed = true)
    private val categoryService = mockk<CategoryService>(relaxed = true)

    private lateinit var viewModel: TasksViewModel


    @Before
    fun setup() {
        coEvery { taskService.getTasksByDate(any()) } returns flowOf(listOf(sampleTask))
        coEvery { categoryService.getCategories() } returns flowOf(listOf(sampleCategory))
        viewModel = object : TasksViewModel(taskService, categoryService) {}
    }

    @Test
    fun `loads initial tasks and categories on init`() = runTest {
        assertEquals(listOf(sampleTask), viewModel.state.value.todoTasks)
        assertEquals(listOf(sampleCategory), viewModel.state.value.categories)
    }

    @Test
    fun `onTabSelected updates selectedTab`() {
        viewModel.onTabSelected(Task.State.DONE)
        assertEquals(Task.State.DONE, viewModel.state.value.selectedTab)
    }

    @Test
    fun `onDateSelectedFromHorizontalRow updates selectedDate and reloads tasks`() = runTest {
        val date = LocalDate(year, month, day)
        coEvery { taskService.getTasksByDate(date) } returns flowOf(listOf(sampleTask))
        viewModel.onDateSelectedFromHorizontalRow(date)
        assertEquals(date, viewModel.state.value.selectedDate)
    }

    @Test
    fun `onDeleteTask sets taskToDelete and shows delete sheet`() {
        viewModel.onDeleteTask(sampleTask)
        assertEquals(sampleTask, viewModel.state.value.taskToDelete)
        assertTrue(viewModel.state.value.showDeleteSheet)
    }

    @Test
    fun `onConfirmDelete deletes task and hides delete sheet`() = runTest {
        viewModel.updateState {
            it.copy(taskToDelete = sampleTask, showDeleteSheet = true)
        }
        coEvery { taskService.deleteTask(sampleTask.id) } just Runs
        viewModel.onConfirmDelete()
        coVerify { taskService.deleteTask(sampleTask.id) }
        assertNull(viewModel.state.value.taskToDelete)
        assertFalse(viewModel.state.value.showDeleteSheet)
    }

    @Test
    fun `onClickSaveTask updates existing task`() = runTest {
        coEvery { taskService.editTask(any()) } just Runs
        viewModel.onClickSaveTask(sampleTask)
        coVerify { taskService.editTask(sampleTask) }
    }

    @Test
    fun `onClickSaveTask creates new task if id is 0`() = runTest {
        val newTask = sampleTask.copy(id)
        coEvery { taskService.createTask(newTask) } just Runs
        viewModel.onClickSaveTask(newTask)
        coVerify { taskService.createTask(newTask) }
    }

    @Test
    fun `toggleAddEditTaskDialog creates new empty task if id is null`() {
        viewModel.toggleAddEditTaskDialog(null)
        val task = viewModel.state.value.initialTask
        assertNotNull(task)
        assertEquals(id, task!!.id)
    }

    @Test
    fun `updateTaskState changes task state and reloads`(): Unit = runTest {
        val updated = sampleTask.copy(state = Task.State.DONE)
        viewModel.updateState { it.copy(todoTasks = listOf(sampleTask)) }
        coEvery { taskService.editTask(updated) } just Runs
        viewModel.updateTaskState(sampleTask.id, Task.State.DONE)
        coVerify { taskService.editTask(updated) }
    }

    @Test
    fun `onDatePickedFromDateDialog updates date, month, year, and loads tasks`() = runTest {

        val selectedDate = LocalDate(year, month, day5)
        coEvery { taskService.getTasksByDate(selectedDate) } returns flowOf(listOf(sampleTask))

        viewModel.onDatePickedFromDateDialog(selectedDate)

        val state = viewModel.state.value
        assertEquals(selectedDate, state.selectedDate)
        assertEquals(selectedDate.year, state.currentYear)
        assertEquals(selectedDate.month, state.currentMonth)

        val expectedDaysInMonth = expectedDaysInMonth
        assertEquals(expectedDaysInMonth, state.monthDates.size)
        assertTrue(state.monthDates.contains(LocalDate(year, month, day)))
        assertTrue(state.monthDates.contains(LocalDate(year, month, expectedDaysInMonth)))

        assertEquals(listOf(sampleTask), state.todoTasks)
    }

    @Test
    fun `onDatePickedFromDateDialog should update date state and load tasks`() = runTest {

        val selectedDate = LocalDate(year, month2, day10)
        val expectedDaysInFeb = expectedDaysInFeb
        coEvery { taskService.getTasksByDate(selectedDate) } returns flowOf(listOf(sampleTask))

        viewModel.onDatePickedFromDateDialog(selectedDate)

        val state = viewModel.state.value

        assertEquals(selectedDate, state.selectedDate)
        assertEquals(year, state.currentYear)
        assertEquals(Month.FEBRUARY, state.currentMonth)
        assertEquals(expectedDaysInFeb, state.monthDates.size)
        assertEquals(LocalDate(year, month2, dayOfMonth), state.monthDates.first())
        assertEquals(LocalDate(year, month2, expectedDaysInFeb), state.monthDates.last())

        assertEquals(listOf(sampleTask), state.todoTasks)
    }


}
