package com.baghdad.tudee.data.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.baghdad.tudee.data.database.TudeeDatabase
import com.baghdad.tudee.data.model.CategoryDto
import com.baghdad.tudee.data.model.TaskDto
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class TaskDaoTest {
    private lateinit var database: TudeeDatabase
    private lateinit var taskDao: TaskDao
    private lateinit var categoryDao: CategoryDao

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TudeeDatabase::class.java
        ).allowMainThreadQueries().build()
        taskDao = database.taskDao()
        categoryDao = database.categoryDao()

    }


    @Test
    fun insertTask_and_getTaskByCategory_correct() = runTest {
       val categoryId =  categoryDao.createCategory(sampleCategoryDto)

        val task = sampleTaskDto.copy(categoryId = categoryId)
        taskDao.createTask(task)

        val result = taskDao.getTasksByCategory(categoryId).first()

        assertEquals(expectedTask, result.size)
        assertEquals(sampleTaskDto.title, result[taskIndex].title)
    }

    @Test
    fun insertTask_and_getByDate_returnsCorrectTask() = runTest {
        val categoryDao = categoryDao.createCategory(sampleCategoryDto)
        val task = sampleTaskDto.copy(categoryId = categoryDao)
        taskDao.createTask(task)

        val result = taskDao.getTasksByDate(date).first()

        assertEquals(expectedTask, result.size)
        assertEquals(sampleTaskDto.description, result.first().description)
    }

    @Test
    fun editTask_updatesTask() = runTest {
        val categoryId = categoryDao.createCategory(sampleCategoryDto)
        val task = sampleTaskDto.copy(categoryId = categoryId)
         taskDao.createTask(task)

        val updatedTask = task.copy(title = taskUpdate)
        taskDao.editTask(updatedTask)

        val result = taskDao.getTasksByCategory(categoryId).first()

        assertEquals(taskUpdate, result.first().title)
    }

    @Test
    fun deleteTask_removesTask() = runTest {
        val categoryId = categoryDao.createCategory(sampleCategoryDto)

        taskDao.deleteTask(categoryId)

        val result = taskDao.getTasksByCategory(categoryId).first()

        assertTrue(result.isEmpty())
    }

    @Test
    fun getTasksByCategory_returnsEmpty_whenNoTask() = runTest {
        val result = taskDao.getTasksByCategory(emptyCategoryId).first()

        assertTrue(result.isEmpty())
    }


    @After
    fun closeDatabase() {
        database.close()
    }

    companion object {
        val expectedTask = 1
        val emptyCategoryId = 999L
        val date ="2025-06-17"
        val taskIndex = 0
        val taskUpdate = "Updated Title"
        val sampleTaskDto = TaskDto(
            id = 1L,
            title = "Test Task",
            description = "desc",
            date = "2025-06-17",
            priority = "LOW",
            categoryId = 0,
            state = "TODO"
        )
        val sampleCategoryDto = CategoryDto(
            id = 0,
            title = "Work",
            imageType = "Predefined",
            imageData = "ic_work",
            imageBytes = byteArrayOf()
        )
    }
}

