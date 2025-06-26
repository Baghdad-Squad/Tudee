package com.baghdad.tudee.ui.screens.categories.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.ui.composable.CategoryItem
import com.baghdad.tudee.ui.model.CategoryUiState
import com.baghdad.tudee.ui.utils.getCategoryIconPainter

@Composable
fun CategoriesList(
    categories: List<CategoryUiState>,
    onCategoryClick: (CategoryUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(104.dp),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 12.dp,
            bottom = (68).dp
        ),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) {
            CategoryItem(
                label = it.title,
                icon = getCategoryIconPainter(
                    it.image ?: Category.Image.Predefined(Category.PredefinedType.ENTERTAINMENT)
                ),
                onClick = {
                    onCategoryClick(it)
                },
                isSelected = false,
                count = it.taskCount,
                isPredefined = it.isPredefined
            )
        }
    }
}