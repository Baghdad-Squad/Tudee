package com.baghdad.tudee.data.service
//a
import android.database.sqlite.SQLiteException
import com.baghdad.tudee.data.database.dao.CategoryDao
import com.baghdad.tudee.data.model.CategoryDto
import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.domain.exception.DatabaseException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CategoryServiceImplTest {
    private lateinit var categoryDao: CategoryDao
    private lateinit var categoryService: CategoryServiceImpl

    // Test data
    private val categoryId = 1L
    private val nonExistentCategoryId = 999L
    private val invalidCategoryId = -1L
    private val expectedTitle = "Test Category"
    private val duplicateTitle = "Duplicate Category"
    private val dbError = "Database error"
    private val sampleCategory = Category(
        id = categoryId,
        title = expectedTitle,
        image = Category.Image.Predefined(Category.PredefinedType.SHOPPING)
    )
    private val sampleDto = CategoryDto(
        id = categoryId,
        title = expectedTitle,
        imageType = "PREDEFINED",
        imageBytes = byteArrayOf()
    )

    @Before
    fun setUp() {
        categoryDao = mockk(relaxed = true)
        categoryService = CategoryServiceImpl(categoryDao)

    }

    @Test
    fun `getCategories returns empty list when no categories exist`() = runTest {
        // Given
        coEvery { categoryDao.getCategories() } returns flowOf(emptyList())

        // When
        val result = categoryService.getCategories().first()

        // Then
        assertTrue(result.isEmpty())
        verify(exactly = 1) {
            categoryDao.getCategories()
        }
    }

    @Test(expected = DatabaseException::class)
    fun `getCategories throws DatabaseException when DAO fails`() = runTest {
        // Given
        coEvery { categoryDao.getCategories() } throws SQLiteException(dbError)

        // When
        categoryService.getCategories().first()
    }

    @Test
    fun `createCategory inserts category successfully`() = runTest {
        // Given
        coEvery { categoryDao.createCategory(any()) } returns 1L

        // When
        val result = categoryService.createCategory(sampleCategory)

        // Then
        assertEquals(1L, result)
        coVerify { categoryDao.createCategory(any()) }
    }

    @Test(expected = DatabaseException::class)
    fun `createCategory throws DatabaseException when DAO fails`() = runTest {
        // Given
        coEvery { categoryDao.createCategory(any()) } throws SQLiteException(dbError)

        // When
        categoryService.createCategory(sampleCategory)
    }

    @Test(expected = DatabaseException::class)
    fun `createCategory throws DatabaseException for duplicate title`() = runTest {
        // Given
        coEvery { categoryDao.createCategory(any()) } throws
                SQLiteException("UNIQUE constraint failed: categories.title")

        // When
        categoryService.createCategory(sampleCategory.copy(title = duplicateTitle))
    }

    @Test
    fun `editCategory updates category successfully`() = runTest {
        // Given
        coEvery { categoryDao.updateCategory(any()) } returns 1

        // When
        val result = categoryService.editCategory(sampleCategory)

        // Then
        assertEquals(1, result)
        coVerify { categoryDao.updateCategory(any()) }
    }

    @Test
    fun `editCategory returns 0 when category not found`() = runTest {
        // Given
        coEvery { categoryDao.updateCategory(any()) } returns 0

        // When
        val result = categoryService.editCategory(sampleCategory.copy(id = nonExistentCategoryId))

        // Then
        assertEquals(0, result)
    }

    @Test(expected = DatabaseException::class)
    fun `editCategory throws DatabaseException when DAO fails`() = runTest {
        // Given
        coEvery { categoryDao.updateCategory(any()) } throws SQLiteException(dbError)

        // When
        categoryService.editCategory(sampleCategory)
    }

    @Test
    fun `deleteCategory deletes category successfully`() = runTest {
        // Given
        coEvery { categoryDao.deleteCategory(categoryId) } returns 1

        // When
        val result = categoryService.deleteCategory(categoryId)

        // Then
        assertEquals(1, result)
        coVerify { categoryDao.deleteCategory(categoryId) }
    }

    @Test
    fun `deleteCategory returns 0 when category not found`() = runTest {
        // Given
        coEvery { categoryDao.deleteCategory(nonExistentCategoryId) } returns 0

        // When
        val result = categoryService.deleteCategory(nonExistentCategoryId)

        // Then
        assertEquals(0, result)
    }

    @Test(expected = DatabaseException::class)
    fun `deleteCategory throws DatabaseException when DAO fails`() = runTest {
        // Given
        coEvery { categoryDao.deleteCategory(categoryId) } throws SQLiteException(dbError)

        // When
        categoryService.deleteCategory(categoryId)
    }

    @Test(expected = DatabaseException::class)
    fun `deleteCategory throws DatabaseException for invalid ID`() = runTest {
        // Given
        coEvery { categoryDao.deleteCategory(invalidCategoryId) } throws
                SQLiteException("Invalid category ID")

        // When
        categoryService.deleteCategory(invalidCategoryId)
    }

    @Test(expected = DatabaseException::class)
    fun `deleteCategory throws DatabaseException when category has dependencies`() = runTest {
        // Given
        coEvery { categoryDao.deleteCategory(categoryId) } throws
                SQLiteException("FOREIGN KEY constraint failed")

        // When
        categoryService.deleteCategory(categoryId)
    }
}