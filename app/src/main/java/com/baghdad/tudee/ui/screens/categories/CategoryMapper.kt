package com.baghdad.tudee.ui.screens.categories

import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.ui.model.CategoryUiState

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