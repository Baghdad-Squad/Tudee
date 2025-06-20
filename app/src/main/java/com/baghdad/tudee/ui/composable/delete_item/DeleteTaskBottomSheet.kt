package com.baghdad.tudee.ui.composable.delete_item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.ui.composable.TudeeBottomSheet
import com.baghdad.tudee.ui.composable.button.NegativeButton

@Composable
fun DeleteTaskBottomSheet(
    onDeleteClick: () -> Unit,
    onCancelClick: () -> Unit,
    isLoading: Boolean = false
) {
    DeleteItemContent(
        title = stringResource(id = R.string.delete_task),
        message = stringResource(R.string.continue_text),
        deleteButtonText = stringResource(R.string.delete),
        cancelButtonText = stringResource(R.string.cancel),
        onDeleteClick = onDeleteClick,
        onCancelClick = onCancelClick,
        isLoading = isLoading
    )
}

@Composable
fun ShowDeleteTaskSheet(
    onDeleteConfirmed: () -> Unit = {},
    onCancelConfirmed: () -> Unit = {},
    isLoading: Boolean = false,
) {
    var showSheet by remember { mutableStateOf(false) }
        TudeeBottomSheet(
            isVisible = showSheet,
            onDismiss = { showSheet = false }
        ) {
            DeleteTaskBottomSheet(
                isLoading = isLoading,
                onDeleteClick = {
                    onDeleteConfirmed()
                    showSheet = false
                },
                onCancelClick = {
                    onCancelConfirmed()
                    showSheet = false
                }
            )
        }
}
