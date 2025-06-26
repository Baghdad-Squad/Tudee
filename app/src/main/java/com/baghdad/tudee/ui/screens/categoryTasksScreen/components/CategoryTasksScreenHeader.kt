package com.baghdad.tudee.ui.screens.categoryTasksScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.ui.screens.categoryTasksScreen.CategoriesTasksInteractionListener
import com.baghdad.tudee.ui.screens.categoryTasksScreen.CategoryTasksScreenUiState


@Composable
fun CategoryTasksScreenHeader(
    state: CategoryTasksScreenUiState,
    onArrowBackClicked: () -> Unit,
    listener: CategoriesTasksInteractionListener,
    modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IconInBox(icon = R.drawable.arrow_left_01, onIconClick = { onArrowBackClicked() })
        Text(
            text = state.category.title,
            style = Theme.typography.title.large,
            color = Theme.color.textColor.title,
            modifier = Modifier.padding(end = 16.dp)
        )
        if (!state.category.isPredefinedCategory) {
            Spacer(modifier = Modifier.weight(1f))
            IconInBox(icon = R.drawable.pencil_edit_02, onIconClick = {
                listener.onToggleEditCategorySheetVisibility()
            }
            )
        }
    }
}