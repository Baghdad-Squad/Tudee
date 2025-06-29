package com.baghdad.tudee.ui.screens.homeScreen

import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.domain.service.AppConfigurationService
import com.baghdad.tudee.domain.service.CategoryService
import com.baghdad.tudee.domain.service.TaskService
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var appConfigurationService: AppConfigurationService
    private lateinit var taskService: TaskService
    private lateinit var categoryService: CategoryService

    private lateinit var viewModel: HomeScreenViewModel

    private val testCategories = listOf(
        Category(
            id = 1L,
            title = "Work",
            image = Category.Image.Predefined(Category.PredefinedType.ENTERTAINMENT)
        ), Category(
            id = 2L,
            title = "Personal",
            image = Category.Image.Predefined(Category.PredefinedType.FAMILY_AND_FRIEND)
        )
    )

    private val testTasks = listOf(
        Task(
            id = 1L,
            title = "Task 1",
            description = "Description 1",
            date = LocalDate(2024, 1, 1),
            priority = Task.Priority.HIGH,
            categoryId = 1L,
            state = Task.State.TODO
        ), Task(
            id = 2L,
            title = "Task 2",
            description = "Description 2",
            date = LocalDate(2024, 1, 1),
            priority = Task.Priority.MEDIUM,
            categoryId = 2L,
            state = Task.State.IN_PROGRESS
        ), Task(
            id = 3L,
            title = "Task 3",
            description = "Description 3",
            date = LocalDate(2024, 1, 1),
            priority = Task.Priority.LOW,
            categoryId = 1L,
            state = Task.State.DONE
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        appConfigurationService = mockk(relaxed = true)
        taskService = mockk(relaxed = true)
        categoryService = mockk(relaxed = true)

        coEvery { appConfigurationService.isDarkTheme() } returns flowOf(false)
        coEvery { categoryService.getCategories() } returns flowOf(testCategories)
        coEvery { taskService.getTasksByDate(any()) } returns flowOf(testTasks)

        viewModel = HomeScreenViewModel(
            appConfigurationService = appConfigurationService,
            taskService = taskService,
            categoryService = categoryService
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `should initialize with correct default state when viewModel is created`() = runTest {
        testScheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertThat(state.inProgressTasks).containsExactly(testTasks[1])
        assertThat(state.todoTasks).containsExactly(testTasks[0])
        assertThat(state.doneTasks).containsExactly(testTasks[2])
        assertThat(state.categories).containsExactlyElementsIn(testCategories)
        assertThat(state.isDark).isFalse()
    }

    @Test
    fun `should load dark theme setting when viewModel is initialized`() = runTest {
        coEvery { appConfigurationService.isDarkTheme() } returns flowOf(true)

        val newViewModel = HomeScreenViewModel(
            appConfigurationService = appConfigurationService,
            taskService = taskService,
            categoryService = categoryService
        )
        testScheduler.advanceUntilIdle()

        assertThat(newViewModel.state.value.isDark).isTrue()
    }

    @Test
    fun `should show task details when getTaskDetailsById is called with valid id`() = runTest {
        testScheduler.advanceUntilIdle()
        val taskId = 1L
        val expectedTask = testTasks[0]

        viewModel.getTaskDetailsById(taskId)

        val state = viewModel.state.value
        assertThat(state.showTaskDetails).isTrue()
        assertThat(state.showAddNewTask).isFalse()
        assertThat(state.showEditTask).isFalse()
        assertThat(state.taskDetailsState.id).isEqualTo(taskId)
        assertThat(state.taskDetailsState.title).isEqualTo(expectedTask.title)
        assertThat(state.taskDetailsState.description).isEqualTo(expectedTask.description)
    }

    @Test
    fun `should not change state when getTaskDetailsById is called with invalid id`() = runTest {
        testScheduler.advanceUntilIdle()
        val initialState = viewModel.state.value

        viewModel.getTaskDetailsById(999L)

        assertThat(viewModel.state.value).isEqualTo(initialState)
    }

    @Test
    fun `should create task successfully when onClickSaveTask is called with new task`() = runTest {
        testScheduler.advanceUntilIdle()
        val newTask = Task(
            id = 0L,
            title = "New Task",
            description = "New Description",
            date = LocalDate(2024, 1, 1),
            priority = Task.Priority.HIGH,
            categoryId = 1L,
            state = Task.State.TODO
        )
        coEvery { taskService.createTask(any()) } just runs
        coEvery { taskService.getTasksByDate(any()) } returns flowOf(testTasks + newTask.copy(id = 4L))

        viewModel.onClickSaveTask(newTask)
        testScheduler.advanceUntilIdle()

        coVerify { taskService.createTask(newTask) }
        assertThat(viewModel.state.value.showAddNewTask).isFalse()
    }

    @Test
    fun `should update task successfully when onClickSaveTask is called with existing task`() =
        runTest {
            testScheduler.advanceUntilIdle()
            val existingTask = testTasks[0].copy(title = "Updated Title")
            coEvery { taskService.editTask(any()) } just runs

            viewModel.onClickSaveTask(existingTask)
            testScheduler.advanceUntilIdle()

            coVerify { taskService.editTask(existingTask) }
            assertThat(viewModel.state.value.showAddNewTask).isFalse()
        }


    @Test
    fun `should toggle add task dialog state when toggleAddNewTaskDialog is called`() {
        val initialShowState = viewModel.state.value.showAddNewTask

        viewModel.toggleAddNewTaskDialog()

        assertThat(viewModel.state.value.showAddNewTask).isEqualTo(!initialShowState)
        assertThat(viewModel.state.value.showEditTask).isFalse()
        assertThat(viewModel.state.value.showTaskDetails).isFalse()
    }

    @Test
    fun `should toggle task details dialog state when toggleTaskDetailsDialog is called`() {
        val initialShowState = viewModel.state.value.showTaskDetails

        viewModel.toggleTaskDetailsDialog()

        assertThat(viewModel.state.value.showTaskDetails).isEqualTo(!initialShowState)
        assertThat(viewModel.state.value.showAddNewTask).isFalse()
        assertThat(viewModel.state.value.showEditTask).isFalse()
    }

    @Test
    fun `should show add task dialog when showAddTaskDialog is called`() {
        viewModel.showAddTaskDialog()

        assertThat(viewModel.state.value.showAddNewTask).isTrue()
        assertThat(viewModel.state.value.showEditTask).isFalse()
        assertThat(viewModel.state.value.showTaskDetails).isFalse()
    }

    @Test
    fun `should show task details dialog when showTaskDetailsDialog is called`() {
        viewModel.showTaskDetailsDialog()

        assertThat(viewModel.state.value.showTaskDetails).isTrue()
        assertThat(viewModel.state.value.showAddNewTask).isFalse()
        assertThat(viewModel.state.value.showEditTask).isFalse()
    }

    @Test
    fun `should move task to done when moveTaskToDone is called with valid todo task`() = runTest {
        testScheduler.advanceUntilIdle()
        val taskId = 1L
        val originalTask = testTasks[0]
        coEvery { taskService.editTask(any()) } just runs

        viewModel.moveTaskToDone(taskId)
        testScheduler.advanceUntilIdle()

        coVerify { taskService.editTask(originalTask.copy(state = Task.State.DONE)) }
    }

    @Test
    fun `should move task to done when moveTaskToDone is called with valid in-progress task`() =
        runTest {
            testScheduler.advanceUntilIdle()
            val taskId = 2L
            val originalTask = testTasks[1]
            coEvery { taskService.editTask(any()) } just runs

            viewModel.moveTaskToDone(taskId)
            testScheduler.advanceUntilIdle()

            coVerify { taskService.editTask(originalTask.copy(state = Task.State.DONE)) }
        }

    @Test
    fun `should move task to todo when moveTaskToTodo is called with valid in-progress task`() =
        runTest {
            testScheduler.advanceUntilIdle()
            val taskId = 2L
            val originalTask = testTasks[1]
            coEvery { taskService.editTask(any()) } just runs

            viewModel.moveTaskToTodo(taskId)
            testScheduler.advanceUntilIdle()

            coVerify { taskService.editTask(originalTask.copy(state = Task.State.TODO)) }
        }

    @Test
    fun `should move task to in-progress when moveTaskToInProgress is called with valid todo task`() =
        runTest {
            testScheduler.advanceUntilIdle()
            val taskId = 1L
            val originalTask = testTasks[0]
            coEvery { taskService.editTask(any()) } just runs

            viewModel.moveTaskToInProgress(taskId)
            testScheduler.advanceUntilIdle()

            coVerify { taskService.editTask(originalTask.copy(state = Task.State.IN_PROGRESS)) }
        }


    @Test
    fun `should toggle theme when onClickSwitchTheme is called`() = runTest {
        testScheduler.advanceUntilIdle()
        val currentTheme = viewModel.state.value.isDark
        coEvery { appConfigurationService.setTheme(any()) } just runs

        viewModel.onClickSwitchTheme()
        testScheduler.advanceUntilIdle()

        coVerify { appConfigurationService.setTheme(!currentTheme) }
    }


    @Test
    fun `should show snackbar with correct message when showSnackbarMessage is called`() {
        val message = "Test message"
        val isError = true

        viewModel.showSnackbarMessage(message, isVisible = true, isError = isError)

        val snackbarState = viewModel.state.value.showSnackBar
        assertThat(snackbarState.message).isEqualTo(message)
        assertThat(snackbarState.isVisible).isTrue()
        assertThat(snackbarState.isError).isEqualTo(isError)
    }


    @Test
    fun `should set slider state to NOTHING_IN_YOUR_LIST when all task lists are empty`() =
        runTest {
            coEvery { taskService.getTasksByDate(any()) } returns flowOf(emptyList())

            val newViewModel = HomeScreenViewModel(
                appConfigurationService = appConfigurationService,
                taskService = taskService,
                categoryService = categoryService
            )
            testScheduler.advanceUntilIdle()

            assertThat(newViewModel.state.value.sliderState).isEqualTo(SliderState.NOTHING_IN_YOUR_LIST)
        }

    @Test
    fun `should set slider state to TADOO when only done tasks exist`() = runTest {
        val onlyDoneTasks = listOf(testTasks[2]) // Only DONE task
        coEvery { taskService.getTasksByDate(any()) } returns flowOf(onlyDoneTasks)

        val newViewModel = HomeScreenViewModel(
            appConfigurationService = appConfigurationService,
            taskService = taskService,
            categoryService = categoryService
        )
        testScheduler.advanceUntilIdle()

        assertThat(newViewModel.state.value.sliderState).isEqualTo(SliderState.TADOO)
    }

    @Test
    fun `should set slider state to ZERO_PROGRESS when only todo tasks exist`() = runTest {
        val onlyTodoTasks = listOf(testTasks[0]) // Only TODO task
        coEvery { taskService.getTasksByDate(any()) } returns flowOf(onlyTodoTasks)

        val newViewModel = HomeScreenViewModel(
            appConfigurationService = appConfigurationService,
            taskService = taskService,
            categoryService = categoryService
        )
        testScheduler.advanceUntilIdle()

        assertThat(newViewModel.state.value.sliderState).isEqualTo(SliderState.ZERO_PROGRESS)
    }

    @Test
    fun `should set slider state to STAY_WORKING when all task types exist`() = runTest {
        testScheduler.advanceUntilIdle()

        assertThat(viewModel.state.value.sliderState).isEqualTo(SliderState.STAY_WORKING)
    }


    @Test
    fun `should handle error when togileEditTaskDialog is called with null id`() = runTest {
        testScheduler.advanceUntilIdle()
        val initialState = viewModel.state.value

        viewModel.togileEditTaskDialog(null)
        testScheduler.advanceUntilIdle()

        assertThat(viewModel.state.value.showEditTask).isEqualTo(initialState.showEditTask)

        testScheduler.advanceTimeBy(5000)
        testScheduler.advanceUntilIdle()
    }

    @Test
    fun `should edit task successfully when onClickEditTask is called`() = runTest {
        testScheduler.advanceUntilIdle()
        val editedTask = testTasks[0].copy(title = "Edited Title")
        viewModel.togileEditTaskDialog(1L)
        testScheduler.advanceUntilIdle()
        coEvery { taskService.editTask(any()) } just runs

        viewModel.onClickEditTask(editedTask)
        testScheduler.advanceUntilIdle()

        coVerify(exactly = 2) { taskService.editTask(any()) } // Once in toggle, once in edit
    }
}