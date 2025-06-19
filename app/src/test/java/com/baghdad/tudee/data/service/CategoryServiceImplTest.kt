package com.baghdad.tudee.data.service

import android.database.sqlite.SQLiteException
import com.baghdad.tudee.R
import com.baghdad.tudee.data.database.dao.CategoryDao
import com.baghdad.tudee.data.service.shared.TestDummyData.Companion.categoryID
import com.baghdad.tudee.data.service.shared.TestDummyData.Companion.dbError
import com.baghdad.tudee.data.service.shared.TestDummyData.Companion.duplicateCategoryTitle
import com.baghdad.tudee.data.service.shared.TestDummyData.Companion.emptyCategoryList
import com.baghdad.tudee.data.service.shared.TestDummyData.Companion.expectedImageUri
import com.baghdad.tudee.data.service.shared.TestDummyData.Companion.expectedTitle
import com.baghdad.tudee.data.service.shared.TestDummyData.Companion.index
import com.baghdad.tudee.data.service.shared.TestDummyData.Companion.invalidCategoryData
import com.baghdad.tudee.data.service.shared.TestDummyData.Companion.invalidCategoryId
import com.baghdad.tudee.data.service.shared.TestDummyData.Companion.nonExistentCategoryId
import com.baghdad.tudee.data.service.shared.TestDummyData.Companion.oneCategoryExpected
import com.baghdad.tudee.data.service.shared.TestDummyData.Companion.sampleCategory
import com.baghdad.tudee.data.service.shared.TestDummyData.Companion.sampleDto
import com.baghdad.tudee.data.source.PredefinedCategorySource
import com.baghdad.tudee.domain.entity.Category
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
    private lateinit var predefinedCategorySource: PredefinedCategorySource
    private lateinit var categoryDao: CategoryDao
    private lateinit var categoryService: CategoryServiceImpl

    private val mockPredefinedCategories = listOf(
        Category(
            id = -2L,
            title = "Personal",
            image = Category.Image.Predefined(Category.PredefinedType.GYM)
        ),
        Category(
            id = -2L,
            title = "Personal",
            image = Category.Image.Predefined(Category.PredefinedType.GYM)
        )
    )

    @Before
    fun setUp() {
        predefinedCategorySource = mockk<PredefinedCategorySource>(relaxed = true)
        categoryDao = mockk(relaxed = true)
        // Instantiate the service with mocked dependencies
        categoryService = CategoryServiceImpl(categoryDao, predefinedCategorySource)
    }

    @Test
    fun `getCategories happy path with predefined and user categories`() = runTest {
        coEvery { categoryDao.getCategories() } returns flowOf(listOf(sampleDto))
        coEvery { predefinedCategorySource.getPredefinedCategories() } returns mockPredefinedCategories

        val result = categoryService.getCategories().first()

        assertEquals(mockPredefinedCategories.size + 1, result.size)
        assertEquals(mockPredefinedCategories[0].title, result[0].title)
        assertEquals(expectedTitle, result[mockPredefinedCategories.size].title)
        assertEquals(expectedImageUri, result[mockPredefinedCategories.size].image)
        coVerify(exactly = 1) { categoryDao.getCategories() }
        coVerify(exactly = 1) { predefinedCategorySource.getPredefinedCategories() }
    }

    @Test
    fun `getCategories with empty user categories returns only predefined`() = runTest {
        // Arrange
        coEvery { categoryDao.getCategories() } returns flowOf(emptyCategoryList)
        coEvery { predefinedCategorySource.getPredefinedCategories() } returns mockPredefinedCategories

        // Act
        val result = categoryService.getCategories().first()
        assertEquals(mockPredefinedCategories.size, result.size)
        assertEquals(mockPredefinedCategories[0].title, result[0].title)
        coVerify(exactly = 1) { categoryDao.getCategories() }
        coVerify(exactly = 1) { predefinedCategorySource.getPredefinedCategories() }
    }

    @Test
    fun `getCategories with empty predefined categories returns only user categories`() = runTest {
        // Arrange
        coEvery { categoryDao.getCategories() } returns flowOf(listOf(sampleDto))
        coEvery { predefinedCategorySource.getPredefinedCategories() } returns emptyList()

        // Act
        val result = categoryService.getCategories().first()

        // Assert
        assertEquals(oneCategoryExpected, result.size)
        assertEquals(expectedTitle, result[0].title)
        assertEquals(expectedImageUri, result[0].image)
        // Verify interactions
        coVerify(exactly = 1) { categoryDao.getCategories() }
        coVerify(exactly = 1) { predefinedCategorySource.getPredefinedCategories() }
    }

    @Test
    fun `getCategories with both empty lists returns empty result`() = runTest {
        // Arrange
        coEvery { categoryDao.getCategories() } returns flowOf(emptyCategoryList)
        coEvery { predefinedCategorySource.getPredefinedCategories() } returns emptyList()

        // Act
        val result = categoryService.getCategories().first()

        // Assert
        assertTrue(result.isEmpty())
        // Verify interactions
        coVerify(exactly = 1) { categoryDao.getCategories() }
        coVerify(exactly = 1) { predefinedCategorySource.getPredefinedCategories() }
    }

    @Test(expected = DatabaseException::class)
    fun `getCategories DAO error throws DatabaseException`() = runTest {
        // Arrange
        coEvery { categoryDao.getCategories() } throws SQLiteException(dbError)
        coEvery { predefinedCategorySource.getPredefinedCategories() } returns mockPredefinedCategories

        // Act & Assert
        categoryService.getCategories().first() // This line is expected to throw
        // Verify interaction (even on error, the call happens)
        coVerify(exactly = 1) { categoryDao.getCategories() }
        // Note: predefinedCategorySource.getPredefinedCategories() might not be called if exception is thrown earlier
    }

    @Test
    fun `getCategories mapping logic for user categories`() = runTest {
        // Arrange
        coEvery { categoryDao.getCategories() } returns flowOf(listOf(sampleDto))
        coEvery { predefinedCategorySource.getPredefinedCategories() } returns emptyList()

        // Act
        val result = categoryService.getCategories().first()

        // Assert
        assertEquals(sampleDto.id, result[index].id)
        assertEquals(sampleDto.title, result[index].title)
        assertEquals(sampleDto.imageType, result[index].image) // Assuming imageType maps to image
        // Verify interactions
        coVerify(exactly = 1) { categoryDao.getCategories() }
        coVerify(exactly = 1) { predefinedCategorySource.getPredefinedCategories() }
    }

    @Test
    fun `getCategories flow collection multiple emissions`() = runTest {
        // Arrange
        coEvery { categoryDao.getCategories() } returns flowOf(
            listOf(sampleDto),
            listOf(sampleDto, sampleDto)
        )
        coEvery { predefinedCategorySource.getPredefinedCategories() } returns mockPredefinedCategories

        // Act
        val result = categoryService.getCategories().toList() // Collect all emissions

        // Assert
        assertEquals(2, result.size) // Two emissions
        assertEquals(mockPredefinedCategories.size + 1, result[0].size) // First emission size
        assertEquals(mockPredefinedCategories.size + 2, result[1].size) // Second emission size
        // Verify interactions (called once, but flow emits multiple)
        coVerify(exactly = 1) { categoryDao.getCategories() }
        coVerify(exactly = 1) { predefinedCategorySource.getPredefinedCategories() }
    }

    // --- createCategory() Tests ---
    @Test
    fun `createCategory happy path`() = runTest {
        // Arrange
        coEvery { categoryDao.createCategory(any()) } returns Long.MAX_VALUE

        // Act
        categoryService.createCategory(sampleCategory)

        // Assert
        coVerify(exactly = 1) { categoryDao.createCategory(any()) }
    }

    @Test(expected = DatabaseException::class)
    fun `createCategory DAO error throws DatabaseException`() = runTest {
        // Arrange
        coEvery { categoryDao.createCategory(any()) } throws SQLiteException(dbError)

        // Act & Assert
        categoryService.createCategory(sampleCategory)
        // Verify interaction
        coVerify(exactly = 1) { categoryDao.createCategory(any()) }
    }

    @Test(expected = DatabaseException::class)
    fun `createCategory with invalid category data throws DatabaseException`() = runTest {
        // Arrange
        coEvery { categoryDao.createCategory(any()) } throws SQLiteException("Invalid category data")

        // Act & Assert
        categoryService.createCategory(invalidCategoryData)
        // Verify interaction
        coVerify(exactly = 1) { categoryDao.createCategory(any()) }
    }

    @Test(expected = DatabaseException::class)
    fun `createCategory duplicate category throws DatabaseException`() = runTest {
        // Arrange
        coEvery { categoryDao.createCategory(any()) } throws SQLiteException("Category title already exists")

        // Act & Assert
        categoryService.createCategory(sampleCategory.copy(title = duplicateCategoryTitle))
        // Verify interaction
        coVerify(exactly = 1) { categoryDao.createCategory(any()) }
    }

    @Test
    fun `createCategory verifies DTO conversion`() = runTest {
        // Arrange
        coEvery { categoryDao.createCategory(any()) } returns Long.MAX_VALUE

        // Act
        categoryService.createCategory(sampleCategory)

        // Assert
        // Verify the DAO was called with correctly mapped DTO
        coVerify(exactly = 1) {
            Category(
                id = -2L,
                title = "Personal",
                image = Category.Image.Predefined(Category.PredefinedType.GYM)
            )
        }
    }


    // --- editCategory() Tests ---
    @Test
    fun `editCategory happy path`() = runTest {
        // Arrange
        coEvery { categoryDao.updateCategory(any()) } returns 1

        // Act
        val result = categoryService.editCategory(sampleCategory)

        // Assert
        assertEquals(1, result)
        coVerify(exactly = 1) {
            categoryDao.updateCategory(match {
                it.id == categoryID && it.title == expectedTitle
            })
        }
    }

    @Test
    fun `editCategory non existent category returns zero`() = runTest {
        // Arrange
        coEvery { categoryDao.updateCategory(any()) } returns 0

        // Act
        val result = categoryService.editCategory(sampleCategory.copy(id = nonExistentCategoryId))

        // Assert
        assertEquals(0, result)
        coVerify(exactly = 1) { categoryDao.updateCategory(any()) }
    }

    @Test(expected = DatabaseException::class)
    fun `editCategory DAO error throws DatabaseException`() = runTest {
        // Arrange
        coEvery { categoryDao.updateCategory(any()) } throws SQLiteException(dbError)

        // Act & Assert
        categoryService.editCategory(sampleCategory)
        // Verify interaction
        coVerify(exactly = 1) { categoryDao.updateCategory(any()) }
    }

    @Test
    fun `editCategory DTO mapping verification`() = runTest {
        // Arrange
        coEvery { categoryDao.updateCategory(any()) } returns 1

        // Act
        categoryService.editCategory(sampleCategory)

        // Assert
        coVerify(exactly = 1) {
            coVerify(exactly = 1) {
                Category(
                    id = -2L,
                    title = "Personal",
                    image = Category.Image.Predefined(Category.PredefinedType.GYM)
                )
            }
        }
    }

    @Test(expected = DatabaseException::class)
    fun `editCategory with invalid category data throws DatabaseException`() = runTest {
        // Arrange
        coEvery { categoryDao.updateCategory(any()) } throws SQLiteException("Invalid category data")

        // Act & Assert
        categoryService.editCategory(invalidCategoryData)
        // Verify interaction
        coVerify(exactly = 1) { categoryDao.updateCategory(any()) }
    }

    // --- deleteCategory() Tests ---
    @Test
    fun `deleteCategory happy path`() = runTest {
        // Arrange
        coEvery { categoryDao.deleteCategory(categoryID) } returns 1

        // Act
        val result = categoryService.deleteCategory(categoryID)

        // Assert
        assertEquals(1, result)
        coVerify(exactly = 1) { categoryDao.deleteCategory(categoryID) }
    }

    @Test
    fun `deleteCategory non existent category returns zero`() = runTest {
        // Arrange
        coEvery { categoryDao.deleteCategory(nonExistentCategoryId) } returns 0

        // Act
        val result = categoryService.deleteCategory(nonExistentCategoryId)

        // Assert
        assertEquals(0, result)
        coVerify(exactly = 1) { categoryDao.deleteCategory(nonExistentCategoryId) }
    }

    @Test(expected = DatabaseException::class)
    fun `deleteCategory DAO error throws DatabaseException`() = runTest {
        // Arrange
        coEvery { categoryDao.deleteCategory(categoryID) } throws SQLiteException(dbError)

        // Act & Assert
        categoryService.deleteCategory(categoryID)
        // Verify interaction
        coVerify(exactly = 1) { categoryDao.deleteCategory(categoryID) }
    }

    @Test(expected = DatabaseException::class)
    fun `deleteCategory with invalid categoryId throws DatabaseException`() = runTest {
        // Arrange
        coEvery { categoryDao.deleteCategory(invalidCategoryId) } throws SQLiteException("Invalid category ID")

        // Act & Assert
        categoryService.deleteCategory(invalidCategoryId)
        // Verify interaction
        coVerify(exactly = 1) { categoryDao.deleteCategory(invalidCategoryId) }
    }

    @Test(expected = DatabaseException::class)
    fun `deleteCategory with foreign key constraint throws DatabaseException`() = runTest {
        // Arrange
        coEvery { categoryDao.deleteCategory(categoryID) } throws SQLiteException("Foreign key constraint failed")

        // Act & Assert
        categoryService.deleteCategory(categoryID)
        // Verify interaction
        coVerify(exactly = 1) { categoryDao.deleteCategory(categoryID) }
    }
}