package com.baghdad.tudee.ui.viewModel.mapper

import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.ui.viewModel.state.CategoryUiState
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
fun CategoryUiState.toEntity(): Category{
    return Category(
        id = id,
        title = title,
        imageUri = imageUri
    )
}

@OptIn(ExperimentalUuidApi::class)
fun Category.toUiState(): CategoryUiState{
    return CategoryUiState(
        id = id,
        title = title,
        imageUri = imageUri
    )
}