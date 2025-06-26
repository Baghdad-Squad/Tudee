package com.baghdad.tudee.ui.screens.categories.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.ui.model.CategoryUiState
import com.baghdad.tudee.ui.shared.components.CategoryItem
import com.baghdad.tudee.ui.utils.getCategoryIconPainter
import com.baghdad.tudee.ui.utils.getCategoryTitle

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
            horizontal = 16.dp,
            vertical = 12.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) {
            CategoryItem(
                label = getCategoryTitle(it),
                icon = getCategoryIconPainter(it.image),
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