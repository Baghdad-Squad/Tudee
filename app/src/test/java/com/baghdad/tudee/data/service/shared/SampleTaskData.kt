package com.baghdad.tudee.data.service.shared

import com.baghdad.tudee.data.mapper.toEntity
import com.baghdad.tudee.data.model.CategoryDto
import com.baghdad.tudee.data.model.TaskDto
import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.domain.entity.Task
import kotlinx.datetime.LocalDate
class TestDummyData() {
    companion object {
        val categoryID1: Long = 10
        val oneTaskExpected = 1
        val date = "2025-06-17"
        val taskindex = 0
        val taskExpectedTitle = "Test Task"
        val expectedDescription = "desc"
        val priorty = "LOW"
        val taskID = 1L
        val dbErrorTask = "DB error"
        const val categoryID: Long = 1L
        const val expectedTitle: String = "Work"
        const val expectedImageUri: String = "content://image/work.jpg"
        const val index: Int = 0
        const val oneCategoryExpected: Int = 1
        const val dbError: String = "Database error"
        const val nonExistentCategoryId: Long = 999L
        const val invalidCategoryId: Long = -1L
        const val duplicateCategoryTitle: String = "Duplicate Work"

        val sampleTaskDto = TaskDto(
            id = 1,
            title = "Test Task",
            description = "desc",
            date = "2025-06-17",
            priority = "LOW",
            categoryId = 10,
            state = "TODO"
        )
        val listOfTasks = listOf(TaskDto(
            id = 1,
            title = "Test Task",
            description = "desc",
            date = "2025-06-17",
            priority = "LOW",
            categoryId = 10,
            state = "TODO"
        ),
            TaskDto(
                id = 2,
                title = "Test Task",
                description = "desc",
                date = "2025-06-17",
                priority = "LOW",
                categoryId = 10,
                state = "TODO"
            ),
            TaskDto(
                id = 3,
                title = "Test Task",
                description = "desc",
                date = "2025-06-17",
                priority = "LOW",
                categoryId = 10,
                state = "TODO"
            ),
        )
        val expectedTasks = listOfTasks.map(TaskDto::toEntity)

        val sampleTask = Task(
            id = 1,
            title = "Test Task",
            description = "desc",
            date = LocalDate.parse("2025-06-17"),
            priority = Task.Priority.LOW,
            categoryId = 10,
            state = Task.State.TODO
        )

        val sampleDto = CategoryDto(
            id = categoryID,
            title = expectedTitle,
            imageUri = expectedImageUri
        )

        val sampleCategory = Category(
            id = categoryID,
            title = expectedTitle,
            imageUri = expectedImageUri
        )

        val invalidCategoryData = Category(
            id = -1L,
            title = "",
            imageUri = ""
        )

        val emptyCategoryList: List<CategoryDto> = emptyList()
    }
}

