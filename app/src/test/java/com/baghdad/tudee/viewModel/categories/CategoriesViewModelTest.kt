package com.baghdad.tudee.viewModel.categories

import com.baghdad.tudee.R
import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.domain.service.CategoryService
import com.baghdad.tudee.domain.service.TaskService
import com.baghdad.tudee.ui.screens.categories.CategoriesScreenEffect
import com.baghdad.tudee.ui.screens.categories.CategoriesViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
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
    fun `getCategories should update state with categories and task counts`() = runTest {
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
            kotlinx.coroutines.delay(50)
        }
        throw AssertionError("Timed out waiting for categories. Current: ${viewModel.state.value.categories}")
    }


}