package com.baghdad.tudee.ui.screens.category.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.ui.composable.CategoryItem
import com.baghdad.tudee.ui.screens.category.CategoryUiState
import com.baghdad.tudee.ui.screens.category.mapper.toDrawable
import com.baghdad.tudee.ui.utils.image.byteArrayToPainter

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
                icon = when (it.image) {
                    is Category.Image.ByteArray -> byteArrayToPainter(
                        it.image.data
                    )

                    is Category.Image.Predefined -> painterResource(
                        id = it.image.type.toDrawable()
                    )
                }
                    ?: painterResource(R.drawable.ic_add_image),
                onClick = {
                    onCategoryClick(it.id)
                },
                isSelected = false,
                count = it.taskCount,
            )

        }

    }
}