package com.baghdad.tudee.ui.screens.categories

import com.baghdad.tudee.R
import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.domain.service.CategoryService
import com.baghdad.tudee.domain.service.TaskService
import com.baghdad.tudee.viewModel.utils.waitUntilCategoriesLoaded
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import org.junit.After
import org.junit.Before
import org.junit.Test

class CategoriesViewModelTest {
    private lateinit var viewModel: CategoriesViewModel
    private lateinit var categoryService: CategoryService
    private lateinit var taskService: TaskService

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        categoryService = mockk()
        taskService = mockk()
        viewModel = CategoriesViewModel(categoryService, taskService)
    }



    @Test
    fun `getCategories should update state with categories`() = runTest {
        // Given
        val categories = listOf(
            Category(1, "Category 1", Category.Image.Predefined(Category.PredefinedType.EDUCATION)),
            Category(2, "Category 2", Category.Image.Predefined(Category.PredefinedType.EDUCATION))
        )

        coEvery { categoryService.getCategories() } returns flowOf(categories)
        coEvery { taskService.getTasksByCategory(any()) } returns flowOf(emptyList())

        // When
        viewModel = CategoriesViewModel(categoryService, taskService)
        viewModel.getCategories()
        waitUntilCategoriesLoaded(expectedSize = categories.size, viewModel = viewModel)

        // Then
        assertEquals(categories.size, viewModel.state.value.categories.size)
    }

    @Test
    fun `getCategories should update state with categories with default task counts`() = runTest {
        // Given
        val categories = listOf(
            Category(1, "Category 1", Category.Image.Predefined(Category.PredefinedType.EDUCATION)),
            Category(2, "Category 2", Category.Image.Predefined(Category.PredefinedType.EDUCATION))
        )

        coEvery { categoryService.getCategories() } returns flowOf(categories)
        coEvery { taskService.getTasksByCategory(any()) } returns flowOf(emptyList())
        viewModel = CategoriesViewModel(categoryService, taskService)

        // When

        viewModel.getCategories()
        waitUntilCategoriesLoaded(expectedSize = categories.size, viewModel = viewModel)


        // Then
        assertEquals(categories.size, viewModel.state.value.categories.size)
        assertEquals(0, viewModel.state.value.categories.first().taskCount)
        assertEquals(0, viewModel.state.value.categories.last().taskCount)

    }

    @Test
    fun `get category should return task count for category without the default value for task count`() =
        runTest {
            // Given
            val categories = listOf(
                Category(
                    1,
                    "Category 1",
                    Category.Image.Predefined(Category.PredefinedType.EDUCATION)
                ),
            )

            coEvery { categoryService.getCategories() } returns flowOf(categories)
            coEvery { taskService.getTasksByCategory(any()) } returns flowOf(
                listOf(
                    Task(
                        id = 1,
                        title = "Task 1",
                        description = "Description 1",
                        state = Task.State.TODO,
                        priority = Task.Priority.HIGH,
                        categoryId = 1,
                        date = LocalDate.Companion.parse("2023-01-01"),
                    )
                )
            )
            viewModel = CategoriesViewModel(categoryService, taskService)
            viewModel.getCategories()
            waitUntilCategoriesLoaded(expectedSize = categories.size, viewModel = viewModel)
            println(viewModel.state.value.categories)

            // Then
            assertEquals(1, viewModel.state.value.categories.first().taskCount)

        }

    @Test
    fun `onAddCategoryClicked then state addCategorySheetState isVisible should be true`() {
        // when
        viewModel.onAddCategoryClicked()
        // then
        assertEquals(true, viewModel.state.value.addCategorySheetState.isVisible)
    }

    @Test
    fun `onUpdateCategoryTitle then state addCategorySheetState categoryTitle should be newTitle`() {
        // when
        viewModel.onUpdateCategoryTitle("newTitle")
        // then
        assertEquals("newTitle", viewModel.state.value.addCategorySheetState.categoryTitle)
    }

    @Test
    fun `onUpdateCategoryImageByteArray then state addCategorySheetState categoryImageByteArray should be newByteArray`() {
        val byteArray = byteArrayOf(1, 2, 3)
        // when
        viewModel.onUpdateCategoryImageByteArray(byteArray)
        // then
        assertEquals(
            byteArray,
            viewModel.state.value.addCategorySheetState.categoryImageByteArray
        )
    }

    @Test
    fun `onDismissAddCategorySheet then state addCategorySheetState isVisible should be false`() {
        // when
        viewModel.onDismissAddCategorySheet()
        // then
        assertEquals(false, viewModel.state.value.addCategorySheetState.isVisible)
    }

    @Test
    fun `getCategories empty list`() {
        // Given
        val categories = emptyList<Category>()
        coEvery { categoryService.getCategories() } returns flowOf(categories)
        coEvery { taskService.getTasksByCategory(any()) } returns flowOf(emptyList())
        // When
        viewModel = CategoriesViewModel(categoryService, taskService)
        viewModel.getCategories()
        // Then
        assertEquals(0, viewModel.state.value.categories.size)
    }

    @Test
    fun `getCategories error`() {
        // Given
        val error = Exception("Test error")
        coEvery { categoryService.getCategories() } throws error
        // When
        viewModel = CategoriesViewModel(categoryService, taskService)
        viewModel.getCategories()
        // Then
        assertEquals(0, viewModel.state.value.categories.size)
    }

    @Test
    fun `getCategories task count error`() {
        // Given
        val categories = listOf(
            Category(1, "Category 1", Category.Image.Predefined(Category.PredefinedType.EDUCATION)),
            Category(2, "Category 2", Category.Image.Predefined(Category.PredefinedType.EDUCATION))
        )
        coEvery { categoryService.getCategories() } returns flowOf(categories)
        coEvery { taskService.getTasksByCategory(any()) } throws Exception("Test error")
        // When
        viewModel = CategoriesViewModel(categoryService, taskService)
        viewModel.getCategories()
        // Then
        assertEquals(0, viewModel.state.value.categories.size)
    }


    @Test
    fun `onAddCategory when categoryService createCategory succeeds should call onAddNewCategorySuccess`() =
        runTest {
            // Given
            coEvery {
                categoryService.createCategory(any())
            } returns Unit

            // When
            viewModel.onAddCategory()
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            coVerify(exactly = 1) {
                categoryService.createCategory(any())
            }

            val snackbarState = viewModel.snackbarState.value
            assertEquals(R.string.added_category_successfully, snackbarState.messageRes)
            assertTrue(snackbarState.isSuccess)
            assertTrue(snackbarState.isVisible)
        }

    @Test
    fun `onAddCategory when categoryService createCategory fails should call onAddNewCategoryError`() =
        runTest {
            // Given
            val expectedException = RuntimeException("Network error")
            coEvery {
                categoryService.createCategory(any())
            } throws expectedException

            // When
            viewModel.onAddCategory()
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            coVerify(exactly = 1) {
                categoryService.createCategory(any())
            }

            val snackbarState = viewModel.snackbarState.value
            assertEquals(R.string.an_error_occurred_while_adding_category, snackbarState.messageRes)
            assertFalse(snackbarState.isSuccess)
            assertTrue(snackbarState.isVisible)
        }

    @Test
    fun `onCategoryClicked should NavigateToCategoryTasks effect`() = runTest {
        // Given
        val categoryId = 123L
        val collectedEffects = mutableListOf<CategoriesScreenEffect>()

        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.effects.collect { effect ->
                collectedEffects.add(effect)
            }
        }

        // When
        viewModel.onCategoryClicked(categoryId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(1, collectedEffects.size)
        val effect = collectedEffects.first()
        assertTrue(effect is CategoriesScreenEffect.NavigateToCategoryTasks)
        assertEquals(
            categoryId,
            (effect as CategoriesScreenEffect.NavigateToCategoryTasks).categoryId
        )

        collectJob.cancel()
    }

    @Test
    fun `onCategoryClicked when categoryId is null should not emit any effect`() = runTest {
        // Given
        val categoryId: Long? = null
        val collectedEffects = mutableListOf<CategoriesScreenEffect>()

        // Start collecting effects
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.effects.collect { effect ->
                collectedEffects.add(effect)
            }
        }

        // When
        viewModel.onCategoryClicked(categoryId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(0, collectedEffects.size)

        collectJob.cancel()
    }
}

