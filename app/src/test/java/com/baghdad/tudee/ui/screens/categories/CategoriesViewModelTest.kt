package com.baghdad.tudee.ui.screens.categories

import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.domain.service.CategoryService
import com.baghdad.tudee.domain.service.TaskService
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
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
    private val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        categoryService = mockk()
        taskService = mockk()
        viewModel = CategoriesViewModel(categoryService, taskService)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
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
        waitUntilCategoriesLoaded(expectedSize = categories.size)

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
        waitUntilCategoriesLoaded(expectedSize = categories.size)


        // Then
        assertEquals(categories.size, viewModel.state.value.categories.size)
        assertEquals(0, viewModel.state.value.categories.first().taskCount)
        assertEquals(0, viewModel.state.value.categories.last().taskCount)

    }

    suspend fun waitUntilCategoriesLoaded(expectedSize: Int, timeoutMs: Long = 3000) {
        val start = System.currentTimeMillis()
        while (System.currentTimeMillis() - start < timeoutMs) {
            if (viewModel.state.value.categories.size == expectedSize) return
            delay(50)
        }
        throw AssertionError("Timed out waiting for categories. Current: ${viewModel.state.value.categories}")
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
            waitUntilCategoriesLoaded(expectedSize = categories.size)
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


}