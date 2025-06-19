package com.baghdad.tudee.ui.screens.category.mapper

import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.ui.screens.category.CategoryUiState
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
fun CategoryUiState.toEntity(): Category {
    return Category(
        id = id,
        title = title,
        image = when (image) {
            is Category.Image.ByteArray -> Category.Image.ByteArray(image.data)
            is Category.Image.Predefined -> Category.Image.Predefined(
                Category.PredefinedType.valueOf(
                    image.type.toString()
                )
            )
        }
    )
}


@OptIn(ExperimentalUuidApi::class)
fun Category.toUiState(): CategoryUiState {
    return CategoryUiState(
        id = id,
        title = title,
        image = when (image) {
            is Category.Image.ByteArray -> Category.Image.ByteArray(image.data)
            is Category.Image.Predefined -> Category.Image.Predefined(image.type)
        }
    )
}

