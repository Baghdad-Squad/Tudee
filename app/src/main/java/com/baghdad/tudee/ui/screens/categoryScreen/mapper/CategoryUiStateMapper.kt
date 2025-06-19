package com.baghdad.tudee.ui.screens.categoryScreen.mapper

import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.ui.screens.categoryScreen.CategoryUiState
import com.baghdad.tudee.ui.screens.categoryScreen.UiImage
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
fun CategoryUiState.toEntity(): Category {
    return Category(
        id = id,
        title = title,
        image =when (image) {
            is UiImage.ByteArrayImage -> Category.Image.ByteArray(image.data)
            is UiImage.PredefinedImage -> Category.Image.Predefined(Category.PredefinedType.valueOf(image.path.toString()))
        }
    )
}


@OptIn(ExperimentalUuidApi::class)
fun Category.toUiState(): CategoryUiState {
    return CategoryUiState(
        id = id,
        title = title,
        image = when (image) {
            is Category.Image.ByteArray -> UiImage.ByteArrayImage(image.data)
            is Category.Image.Predefined -> UiImage.PredefinedImage(image.type.toDrawable())
        }
    )
}

