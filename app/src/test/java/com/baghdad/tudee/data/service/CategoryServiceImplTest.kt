package com.baghdad.tudee.data.service

import android.database.sqlite.SQLiteException
import com.baghdad.tudee.data.database.dao.CategoryDao
import com.baghdad.tudee.data.model.CategoryDto
import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.domain.exception.DatabaseException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CategoryServiceImplTest {
    private lateinit var categoryDao: CategoryDao
    private lateinit var categoryService: CategoryServiceImpl

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


    @Before
    fun setUp() {
        categoryDao = mockk(relaxed = true)
        categoryService = CategoryServiceImpl(categoryDao)

    }

    @Test
    fun `getCategories returns empty list when no categories exist`() = runTest {

        coEvery { categoryDao.getCategories() } returns flowOf(emptyList())

        val result = categoryService.getCategories().first()

        assertTrue(result.isEmpty())
        verify(exactly = 1) {
            categoryDao.getCategories()
        }
    }

    @Test(expected = DatabaseException::class)
    fun `getCategories throws DatabaseException when DAO fails`() = runTest {

        coEvery { categoryDao.getCategories() } throws SQLiteException(dbError)

        categoryService.getCategories().first()
    }

    @Test
    fun `createCategory inserts category successfully`() = runTest {

        coEvery { categoryDao.createCategory(any()) } returns 1L

       categoryService.createCategory(sampleCategory)

        coVerify { categoryDao.createCategory(any()) }
    }

    @Test(expected = DatabaseException::class)
    fun `createCategory throws DatabaseException when DAO fails`() = runTest {

        coEvery { categoryDao.createCategory(any()) } throws SQLiteException(dbError)

        categoryService.createCategory(sampleCategory)
    }

    @Test(expected = DatabaseException::class)
    fun `createCategory throws DatabaseException for duplicate title`() = runTest {
        coEvery { categoryDao.createCategory(any()) } throws
                SQLiteException("UNIQUE constraint failed: categories.title")

        categoryService.createCategory(sampleCategory.copy(title = duplicateTitle))
    }

    @Test
    fun `editCategory updates category successfully`() = runTest {

        coEvery { categoryDao.updateCategory(any()) } returns 1

         categoryService.editCategory(sampleCategory)

        coVerify { categoryDao.updateCategory(any()) }
    }

    @Test
    fun `editCategory returns 0 when category not found`() = runTest {
        coEvery { categoryDao.updateCategory(any()) } returns 0

        categoryService.editCategory(sampleCategory.copy(id = nonExistentCategoryId))

        coVerify { categoryDao.updateCategory(any()) }
    }

    @Test(expected = DatabaseException::class)
    fun `editCategory throws DatabaseException when DAO fails`() = runTest {

        coEvery { categoryDao.updateCategory(any()) } throws SQLiteException(dbError)

        categoryService.editCategory(sampleCategory)
    }

    @Test
    fun `deleteCategory deletes category successfully`() = runTest {

        coEvery { categoryDao.deleteCategory(categoryId) } returns 1

         categoryService.deleteCategory(categoryId)

        coVerify { categoryDao.deleteCategory(categoryId) }
    }

    @Test
    fun `deleteCategory returns 0 when category not found`() = runTest {

        coEvery { categoryDao.deleteCategory(nonExistentCategoryId) } returns 0

         categoryService.deleteCategory(nonExistentCategoryId)

       coVerify { categoryDao.deleteCategory(nonExistentCategoryId) }
    }

    @Test(expected = DatabaseException::class)
    fun `deleteCategory throws DatabaseException when DAO fails`() = runTest {

        coEvery { categoryDao.deleteCategory(categoryId) } throws SQLiteException(dbError)

        categoryService.deleteCategory(categoryId)
    }

    @Test(expected = DatabaseException::class)
    fun `deleteCategory throws DatabaseException for invalid ID`() = runTest {
        coEvery { categoryDao.deleteCategory(invalidCategoryId) } throws
                SQLiteException("Invalid category ID")


        categoryService.deleteCategory(invalidCategoryId)
    }

    @Test(expected = DatabaseException::class)
    fun `deleteCategory throws DatabaseException when category has dependencies`() = runTest {

        coEvery { categoryDao.deleteCategory(categoryId) } throws
                SQLiteException("FOREIGN KEY constraint failed")

        categoryService.deleteCategory(categoryId)
    }

    @Test
    fun `getCategoryIdBy return correct category`() = runTest {
        coEvery { categoryDao.getCategoryById(categoryId) } returns CategoryDto(
            id = categoryId,
            title = expectedTitle,
            imageType = CategoryDto.Companion.IMAGE_TYPE_PREDEFINED,
            imageBytes = byteArrayOf(),
            imageData = Category.PredefinedType.WORK.name

        )
        val result = categoryService.getCategoryById(categoryId)

        assertEquals(sampleCategory.id, result?.id)
        assertEquals(sampleCategory.title, result?.title)

    }

    @Test
    fun `getCategoryIdBy not return any category`() = runTest {
        coEvery { categoryDao.getCategoryById(2) } returns null

        val result = categoryService.getCategoryById(2)

        assertNull(result)

    }


}
