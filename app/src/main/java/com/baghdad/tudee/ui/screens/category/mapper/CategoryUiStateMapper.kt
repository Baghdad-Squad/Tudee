package com.baghdad.tudee.ui.screens.category.mapper

import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.ui.screens.category.CategoryUiState


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

