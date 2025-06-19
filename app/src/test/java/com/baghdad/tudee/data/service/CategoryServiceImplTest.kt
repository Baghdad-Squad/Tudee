package com.baghdad.tudee.data.service

import android.database.sqlite.SQLiteException
import com.baghdad.tudee.data.database.dao.CategoryDao
import com.baghdad.tudee.data.service.SampleCategoryData.categoryID
import com.baghdad.tudee.data.service.SampleCategoryData.dbError
import com.baghdad.tudee.data.service.SampleCategoryData.duplicateCategoryTitle
import com.baghdad.tudee.data.service.SampleCategoryData.emptyCategoryList
import com.baghdad.tudee.data.service.SampleCategoryData.expectedImageUri
import com.baghdad.tudee.data.service.SampleCategoryData.expectedTitle
import com.baghdad.tudee.data.service.SampleCategoryData.index
import com.baghdad.tudee.data.service.SampleCategoryData.invalidCategoryData
import com.baghdad.tudee.data.service.SampleCategoryData.invalidCategoryId
import com.baghdad.tudee.data.service.SampleCategoryData.nonExistentCategoryId
import com.baghdad.tudee.data.service.SampleCategoryData.oneCategoryExpected
import com.baghdad.tudee.data.service.SampleCategoryData.sampleCategory
import com.baghdad.tudee.data.service.SampleCategoryData.sampleDto
import com.baghdad.tudee.domain.exception.DatabaseException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CategoryServiceImplTest {

    private val categoryDao: CategoryDao = mockk(relaxed = true)
    private lateinit var categoryService: CategoryServiceImpl

    @Before
    fun setUp() {
        categoryService = CategoryServiceImpl(categoryDao)
    }

    @Test
    fun `getCategories happy path`() = runTest {
        coEvery { categoryDao.getCategories() } returns flowOf(listOf(sampleDto))

        val result = categoryService.getCategories().first()

        assertEquals(oneCategoryExpected, result.size)
        assertEquals(expectedTitle, result[index].title)
        assertEquals(expectedImageUri, result[index].imageUri)
    }

    @Test
    fun `getCategories empty list`() = runTest {
        coEvery { categoryDao.getCategories() } returns flowOf(emptyCategoryList)

        val result = categoryService.getCategories().first()

        assertTrue(result.isEmpty())
    }

    @Test(expected = DatabaseException::class)
    fun `getCategories DAO error`() = runTest {
        coEvery { categoryDao.getCategories() } throws SQLiteException(dbError)

        categoryService.getCategories().first()
    }

    @Test
    fun `getCategories mapping logic`() = runTest {
        coEvery { categoryDao.getCategories() } returns flowOf(listOf(sampleDto))

        val result = categoryService.getCategories().first()

        assertEquals(sampleDto.id, result[index].id)
        assertEquals(sampleDto.title, result[index].title)
        assertEquals(sampleDto.imageUri, result[index].imageUri)
    }

    @Test
    fun `getCategories flow collection`() = runTest {
        coEvery { categoryDao.getCategories() } returns flowOf(listOf(sampleDto))

        val result = categoryService.getCategories().toList()

        assertEquals(1, result.size)
        assertEquals(oneCategoryExpected, result[0].size)
    }


    @Test(expected = DatabaseException::class)
    fun `createCategory DAO error`() = runTest {
        coEvery { categoryDao.createCategory(any()) } throws SQLiteException(dbError)

        categoryService.createCategory(sampleCategory)
    }

    @Test(expected = DatabaseException::class)
    fun `createCategory with invalid category data`() = runTest {
        coEvery { categoryDao.createCategory(any()) } throws SQLiteException("Invalid category data")

        categoryService.createCategory(invalidCategoryData)
    }

    @Test(expected = DatabaseException::class)
    fun `createCategory duplicate category if applicable`() = runTest {
        coEvery { categoryDao.createCategory(any()) } throws SQLiteException("Category title already exists")

        categoryService.createCategory(sampleCategory.copy(title = duplicateCategoryTitle))
    }

    @Test
    fun `editCategory happy path`() = runTest {
        coEvery { categoryDao.updateCategory(any()) } returns 1

        val result = categoryService.editCategory(sampleCategory)

        assertEquals(1, result)
        coVerify {
            categoryDao.updateCategory(match {
                it.id == categoryID && it.title == expectedTitle
            })
        }
    }

    @Test
    fun `editCategory non existent category`() = runTest {
        coEvery { categoryDao.updateCategory(any()) } returns 0

        val result = categoryService.editCategory(sampleCategory.copy(id = nonExistentCategoryId))

        assertEquals(0, result)
    }

    @Test(expected = DatabaseException::class)
    fun `editCategory DAO error`() = runTest {
        coEvery { categoryDao.updateCategory(any()) } throws SQLiteException(dbError)

        categoryService.editCategory(sampleCategory)
    }

    @Test
    fun `editCategory DTO mapping`() = runTest {
        coEvery { categoryDao.updateCategory(any()) } returns 1

        categoryService.editCategory(sampleCategory)

        coVerify {
            categoryDao.updateCategory(match {
                it.id == sampleCategory.id &&
                        it.title == sampleCategory.title &&
                        it.imageUri == sampleCategory.imageUri
            })
        }
    }

    @Test(expected = DatabaseException::class)
    fun `editCategory with invalid category data`() = runTest {
        coEvery { categoryDao.updateCategory(any()) } throws SQLiteException("Invalid category data")

        categoryService.editCategory(invalidCategoryData)
    }

    @Test
    fun `deleteCategory happy path`() = runTest {
        coEvery { categoryDao.deleteCategory(categoryID) } returns 1

        val result = categoryService.deleteCategory(categoryID)

        assertEquals(1, result)
        coVerify { categoryDao.deleteCategory(categoryID) }
    }

    @Test
    fun `deleteCategory non existent category`() = runTest {
        coEvery { categoryDao.deleteCategory(nonExistentCategoryId) } returns 0

        val result = categoryService.deleteCategory(nonExistentCategoryId)

        assertEquals(0, result)
    }

    @Test(expected = DatabaseException::class)
    fun `deleteCategory DAO error`() = runTest {
        coEvery { categoryDao.deleteCategory(categoryID) } throws SQLiteException(dbError)

        categoryService.deleteCategory(categoryID)
    }

    @Test(expected = DatabaseException::class)
    fun `deleteCategory with invalid categoryId`() = runTest {
        coEvery { categoryDao.deleteCategory(invalidCategoryId) } throws SQLiteException("Invalid category ID")

        categoryService.deleteCategory(invalidCategoryId)
    }

    @Test(expected = DatabaseException::class)
    fun `deleteCategory category with dependencies if applicable`() = runTest {
        coEvery { categoryDao.deleteCategory(categoryID) } throws SQLiteException("Foreign key constraint failed")

        categoryService.deleteCategory(categoryID)
    }

}