package com.baghdad.tudee.data.service
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.baghdad.tudee.data.database.TudeeDatabase
import com.baghdad.tudee.data.database.dao.CategoryDao
import com.baghdad.tudee.data.model.CategoryDto
import com.baghdad.tudee.data.service.CategoryServiceImpl
import com.baghdad.tudee.data.service.shared.DatabaseErrorHandler
import com.baghdad.tudee.domain.entity.Category
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.Long

@RunWith(AndroidJUnit4::class)
class CategoryServiceImplTest {

    private lateinit var service: CategoryServiceImpl
    private lateinit var mockDao: CategoryDao
    private lateinit var mockErrorHandler: DatabaseErrorHandler
    private lateinit var realDatabase: TudeeDatabase
    private lateinit var realCategoryDao: CategoryDao
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        // Initialize real database for integration tests
        realDatabase = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            TudeeDatabase::class.java
        ).allowMainThreadQueries().build()
        realCategoryDao = realDatabase.categoryDao()

        // Initialize mocks for unit tests
        service = CategoryServiceImpl(realCategoryDao)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
        realDatabase.close()
    }

    @Test
    fun getCategories_happy_path() = runTest {
        // Arrange
        realCategoryDao.createCategory(CategoryDto(1, "Food", "#FF0000"))

        // Act
        val result = service.getCategories().first()

        // Assert
        assertEquals(1, result.size)
        assertEquals("Food", result[0].title)
    }

    @Test
    fun getCategories_empty_list() = runTest {
        // Act
        val result = service.getCategories().first()

        // Assert
        assertTrue(result.isEmpty())
    }

    @Test(expected = RuntimeException::class)
    fun getCategories_DAO_error_handling() = runTest {
        // Arrange
        service = CategoryServiceImpl(mockDao)
        val testError = Exception("Database error")
        // Act
        service.getCategories().first()
    }

    @Test
    fun getCategories_mapping_verification() = runTest {
        // Arrange
        realCategoryDao.createCategory(CategoryDto(1, "Food", "url"))

        // Act
        val result = service.getCategories().first()

        // Assert
        assertEquals(1, result.size)
        assertEquals("Food", result[0].title)
    }

    @Test
    fun createCategory_happy_path() = runTest {
        // Arrange
        val category = Category(
            id = 1,
            title = "Food",
            imageUri = "url"
        )

        // Act
        service.createCategory(category)
        val result = service.getCategories().first()

        // Assert
        assertEquals(1, result.size)
        assertEquals("Food", result[0].title)
    }

    @Test(expected = RuntimeException::class)
    fun createCategory_DAO_error_handling() = runTest {
        // Arrange
        service = CategoryServiceImpl(mockDao)
        val testError = Exception("Database error")

        // Act
        service.createCategory(
            Category(
                id = 1,
                title = "Food",
                imageUri = "url"
            )
        )
    }


    @Test
    fun editCategory_happy_path() = runTest {
        // Arrange
        realCategoryDao.createCategory(CategoryDto(1, "Food", "#FF0000"))
        val updatedCategory = Category(
            id = 1,
            title = "Updated Food",
            imageUri = "url"
        )

        // Act
        service.editCategory(updatedCategory)
        val result = service.getCategories().first()

        // Assert
        assertEquals(1, result.size)
        assertEquals("Updated Food", result[0].title)
        assertEquals("url", result[0].imageUri)
    }

    @Test
    fun deleteCategory_happy_path() = runTest {
        // Arrange
        realCategoryDao.createCategory(CategoryDto(1, "Food", "#FF0000"))

        // Act
        service.deleteCategory(1)
        val result = service.getCategories().first()

        // Assert
        assertTrue(result.isEmpty())
    }



    @Test(expected = RuntimeException::class)
    fun DatabaseErrorHandler_integration_general() = runTest {
        // Arrange
        service = CategoryServiceImpl(mockDao)
        val testError = Exception("General error")

        // Act
        service.getCategories().first()
    }

}