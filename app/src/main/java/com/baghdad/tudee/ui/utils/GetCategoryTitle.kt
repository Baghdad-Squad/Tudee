package com.baghdad.tudee.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.ui.model.CategoryUiState

@Composable
fun getCategoryTitle(category: CategoryUiState): String {
    return  when(category.image){
        is Category.Image.Predefined -> stringResource(category.image.type.getLabelResId())
        else -> category.title
    }
}