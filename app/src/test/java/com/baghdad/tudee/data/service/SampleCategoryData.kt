package com.baghdad.tudee.data.service

import com.baghdad.tudee.data.model.CategoryDto
import com.baghdad.tudee.domain.entity.Category

object SampleCategoryData {
    const val categoryID: Long = 1L
    const val expectedTitle: String = "Work"
    const val expectedImageUri: String = "content://image/work.jpg"
    const val index: Int = 0
    const val oneCategoryExpected: Int = 1
    const val dbError: String = "Database error"
    const val nonExistentCategoryId: Long = 999L
    const val invalidCategoryId: Long = -1L
    const val duplicateCategoryTitle: String = "Duplicate Work"

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