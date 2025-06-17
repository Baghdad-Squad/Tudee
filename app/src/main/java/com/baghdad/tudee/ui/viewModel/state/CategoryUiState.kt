package com.baghdad.tudee.ui.viewModel.state

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class CategoryUiState @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid,
    val title: String,
    val imageUri: String,
    val taskCount: Int? = null
)
