package com.baghdad.tudee.presentation.model

import com.baghdad.tudee.domain.entity.Category

data class CategoryUiState(
    val id: Long? = null,
    val title: String = "",
    val isPredefined: Boolean = true,
    val image: Category.Image? = null,
    val taskCount: Int = 0
)

fun Category.toUiState(): CategoryUiState {
    return CategoryUiState(
        id = id,
        title = title,
        image = when (image) {
            is Category.Image.ByteArray -> Category.Image.ByteArray(image.data)
            is Category.Image.Predefined -> Category.Image.Predefined(image.type)

        },
        isPredefined = isPredefinedCategory
    )
}

suspend fun List<Category>.toUiStates(
    taskCountProvider: suspend (categoryId: Long) -> Int
) = this.map { category ->
    category.toUiState().copy(
        taskCount = taskCountProvider(category.id)
    )
}