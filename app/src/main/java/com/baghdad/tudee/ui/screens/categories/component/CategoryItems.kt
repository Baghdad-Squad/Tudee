package com.baghdad.tudee.ui.screens.categories.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.ui.composable.CategoryItem
import com.baghdad.tudee.ui.model.CategoryUiState
import com.baghdad.tudee.ui.utils.getCategoryIconPainter

@Composable
fun CategoryItems(state : List<CategoryUiState>, onCategoryClick : (Long) -> Unit){
    LazyVerticalGrid(
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
        items(state) {
            CategoryItem(
                label = it.title,
                icon = getCategoryIconPainter(it.image),
                onClick = {
                    onCategoryClick(it.id)
                },
                isSelected = false,
                count = it.taskCount,
                isPredefined = it.isPredefined
            )

        }

    }
}