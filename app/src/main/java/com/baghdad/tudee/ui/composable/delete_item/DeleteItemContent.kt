package com.baghdad.tudee.ui.composable.delete_item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.Theme


@Composable
fun DeleteItemContent(
    title: String,
    message: String,
    deleteButtonText: String,
    cancelButtonText: String,
    onDeleteClick: () -> Unit,
    onCancelClick: () -> Unit,
    isLoading: Boolean = false
) {
    Box(
        modifier = Modifier
            .padding(top = 24.dp)
            .background(
                color = Theme.color.surfaceColor.surface,
                shape = RoundedCornerShape(24.dp)
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DeleteItemBottomSheetHeader(title, message)

            Box(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .background(color = Theme.color.surfaceColor.surfaceHigh)
                    .padding(vertical = 12.dp, horizontal = 16.dp)
            ) {
                DeleteItemBottomSheetButtons(
                    deleteButtonText,
                    cancelButtonText,
                    onDeleteClick,
                    onCancelClick,
                    isLoading
                )
            }
        }
    }
}
