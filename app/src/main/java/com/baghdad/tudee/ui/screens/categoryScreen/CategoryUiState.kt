package com.baghdad.tudee.ui.screens.categoryScreen

import androidx.annotation.DrawableRes
import com.baghdad.tudee.domain.entity.Category


data class CategoryUiState(
    val id: Long = 0,
    val title: String,
    val image: Category.Image,
    val taskCount: Int = 0
)