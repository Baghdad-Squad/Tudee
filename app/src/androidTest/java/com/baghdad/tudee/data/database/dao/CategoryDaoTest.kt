package com.baghdad.tudee.data.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.baghdad.tudee.data.database.TudeeDatabase
import com.baghdad.tudee.data.model.CategoryDto
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CategoryDaoTest {
    private lateinit var categoryDao: CategoryDao
    private lateinit var database: TudeeDatabase

    @Before
    fun createDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TudeeDatabase::class.java
        ).allowMainThreadQueries().build()
        categoryDao = database.categoryDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }

    // Helper function to create test categories
    private fun createTestCategory(
        id: Long = 0L,
        title: String = "Test",
        imageType: String = CategoryDto.Companion.IMAGE_TYPE_PREDEFINED,
        imageBytes: ByteArray = "test".toByteArray()
    ) = CategoryDto(
        id = id,
        title = title,
        imageType = imageType,
        imageBytes = imageBytes
    )

    @Test
    fun createCategory_insertsSuccessfully() = runTest {
        // Given
        val category = createTestCategory(title = "New Category")

        // When
        val insertedId = categoryDao.createCategory(category)

        // Then
        Assert.assertTrue(insertedId > 0)

        val categories = categoryDao.getCategories().first()
        Assert.assertEquals(1, categories.size)
        Assert.assertEquals("New Category", categories[0].title)
    }

    @Test
    fun createCategory_replacesExisting() = runTest {
        // Given
        val original = createTestCategory(id = 1L, title = "Original")
        val updated = createTestCategory(id = 1L, title = "Updated")

        // When
        categoryDao.createCategory(original)
        categoryDao.createCategory(updated)

        // Then
        val categories = categoryDao.getCategories().first()
        Assert.assertEquals(1, categories.size)
        Assert.assertEquals("Updated", categories[0].title)
    }

    @Test
    fun getCategories_returnsEmptyForEmptyTable() = runTest {
        // When
        val categories = categoryDao.getCategories().first()

        // Then
        Assert.assertTrue(categories.isEmpty())
    }

    @Test
    fun getCategories_returnsAllCategories() = runTest {
        // Given
        val category1 = createTestCategory(title = "Category 1")
        val category2 = createTestCategory(title = "Category 2")
        val category3 = createTestCategory(title = "Category 3")

        listOf(category1, category2, category3).forEach {
            categoryDao.createCategory(it)
        }

        // When
        val categories = categoryDao.getCategories().first()

        // Then
        Assert.assertEquals(3, categories.size)
    }

    @Test
    fun updateCategory_updatesSuccessfully() = runTest {
        // Given
        val original = createTestCategory(title = "Original")
        val insertedId = categoryDao.createCategory(original)
        val inserted = categoryDao.getCategories().first().first()

        // When
        val updated = inserted.copy(title = "Updated")
        val rowsAffected = categoryDao.updateCategory(updated)

        // Then
        Assert.assertEquals(1, rowsAffected)
        Assert.assertEquals("Updated", categoryDao.getCategories().first().first().title)
    }

    @Test
    fun updateCategory_returnsZeroForNonExisting() = runTest {
        // Given
        val nonExisting = createTestCategory(id = 999L, title = "Non-existing")

        // When
        val rowsAffected = categoryDao.updateCategory(nonExisting)

        // Then
        Assert.assertEquals(0, rowsAffected)
    }

    @Test
    fun deleteCategory_deletesSuccessfully() = runTest {
        // Given
        val category = createTestCategory(title = "To Delete")
        categoryDao.createCategory(category)
        val insertedId = categoryDao.getCategories().first().first().id

        // When
        val rowsAffected = categoryDao.deleteCategory(insertedId)

        // Then
        Assert.assertEquals(1, rowsAffected)
        Assert.assertTrue(categoryDao.getCategories().first().isEmpty())
    }

    @Test
    fun deleteCategory_returnsZeroForNonExisting() = runTest {
        // When
        val rowsAffected = categoryDao.deleteCategory(999L)

        // Then
        Assert.assertEquals(0, rowsAffected)
    }

    @Test
    fun imageTypes_areHandledCorrectly() = runTest {
        // Given
        val predefined = createTestCategory(
            title = "Predefined",
            imageType = CategoryDto.Companion.IMAGE_TYPE_PREDEFINED
        )
        val byteArray = createTestCategory(
            title = "ByteArray",
            imageType = CategoryDto.Companion.IMAGE_TYPE_BYTE_ARRAY,
            imageBytes = "byte array".toByteArray()
        )
        // When
        listOf(predefined, byteArray).forEach {
            categoryDao.createCategory(it)
        }

        // Then
        val categories = categoryDao.getCategories().first()
        Assert.assertEquals(3, categories.size)
        Assert.assertEquals(CategoryDto.Companion.IMAGE_TYPE_PREDEFINED, categories[0].imageType)
        Assert.assertEquals(CategoryDto.Companion.IMAGE_TYPE_BYTE_ARRAY, categories[1].imageType)
    }

    @Test
    fun equalsAndHashCode_workCorrectly() = runTest {
        // Given
        val category1 = createTestCategory(id = 1L, title = "Test")
        val category2 = createTestCategory(id = 1L, title = "Test")
        val category3 = createTestCategory(id = 2L, title = "Different")

        // Then
        Assert.assertEquals(category1, category2)
        Assert.assertNotEquals(category1, category3)
        Assert.assertEquals(category1.hashCode(), category2.hashCode())
        Assert.assertNotEquals(category1.hashCode(), category3.hashCode())
    }
}