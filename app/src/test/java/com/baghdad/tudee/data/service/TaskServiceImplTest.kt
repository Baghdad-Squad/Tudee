package com.baghdad.tudee.data.service

import android.database.sqlite.SQLiteException
import com.baghdad.tudee.data.database.dao.TaskDao
import com.baghdad.tudee.data.service.SampleTaskData.categoryID1
import com.baghdad.tudee.data.service.SampleTaskData.date
import com.baghdad.tudee.data.service.SampleTaskData.dbErrorTask
import com.baghdad.tudee.data.service.SampleTaskData.expectedDescription
import com.baghdad.tudee.data.service.SampleTaskData.taskExpectedTitle
import com.baghdad.tudee.data.service.SampleTaskData.taskindex
import com.baghdad.tudee.data.service.SampleTaskData.oneTaskExpected
import com.baghdad.tudee.data.service.SampleTaskData.priorty
import com.baghdad.tudee.data.service.SampleTaskData.sampleTaskDto
import com.baghdad.tudee.data.service.SampleTaskData.sampleTask
import com.baghdad.tudee.data.service.SampleTaskData.taskID
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.domain.exception.DatabaseException
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TaskServiceImplTest {

    private val taskDao: TaskDao = mockk(relaxed = true)
    private lateinit var taskService: TaskServiceImpl

    @Before
    fun setUp() {
        taskService = TaskServiceImpl(taskDao)
    }

    @Test
    fun `getTasksByCategory returns mapped tasks`() = runTest {

        coEvery { taskDao.getTasksByCategory(categoryID1) } returns flowOf(listOf(sampleTaskDto))

        val result = taskService.getTasksByCategory(categoryID1).first()

        assertEquals(oneTaskExpected, result.size)
        assertEquals(taskExpectedTitle, result[taskindex].title)
        assertEquals(Task.State.TODO, result[taskindex].state)
    }

    @Test
    fun `getTasksByDate returns mapped tasks`() = runTest {

        coEvery { taskDao.getTasksByDate(date) } returns flowOf(listOf(sampleTaskDto))

        val result = taskService.getTasksByDate(LocalDate.parse(date)).first()

        assertEquals(oneTaskExpected, result.size)
        assertEquals(expectedDescription, result[taskindex].description)
        assertEquals(Task.Priority.LOW, result[taskindex].priority)
    }

    @Test
    fun `createTask calls DAO with DTO`() = runTest {

        coEvery { taskDao.createTask(any()) } just Runs

        taskService.createTask(sampleTask)

        coVerify {
            taskDao.createTask(match {
                it.title == taskExpectedTitle && it.priority == priorty && it.date == date
            })
        }
    }

    @Test
    fun `editTask calls DAO with DTO`() = runTest {

        coEvery { taskDao.editTask(any()) } just Runs

        taskService.editTask(sampleTask)

        coVerify { taskDao.editTask(match { it.id == taskID && it.title == taskExpectedTitle }) }
    }

    @Test
    fun `deleteTask calls DAO with taskId`() = runTest {

        coEvery { taskDao.deleteTask(taskID) } just Runs

        taskService.deleteTask(taskID)

        coVerify { taskDao.deleteTask(taskID) }
    }

    @Test(expected = DatabaseException::class)
    fun `getTasksByCategory throws DatabaseException on SQLiteException`() = runTest {

        coEvery { taskDao.getTasksByCategory(categoryID1) } throws SQLiteException(dbErrorTask)

        taskService.getTasksByCategory(categoryID1).first()
    }

}

