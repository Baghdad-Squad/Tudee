package com.baghdad.tudee.ui.viewModel.state

import androidx.annotation.DrawableRes

sealed class UiImage {
    data class ByteArrayImage(val data: ByteArray) : UiImage()
    data class PredefinedImage(@DrawableRes val path: Int) : UiImage()
}

data class CategoryUiState(
    val id: Long = 0,
    val title: String,
    val image: UiImage,
    val taskCount: Int = 0
)