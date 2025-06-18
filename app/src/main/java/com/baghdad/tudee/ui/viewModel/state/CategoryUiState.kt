package com.baghdad.tudee.ui.viewModel.state

import kotlin.uuid.ExperimentalUuidApi

data class CategoryUiState @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Long? = null,
    val title: String,
    val image: ByteArray,
    val taskCount: Int? = null
)
