package com.baghdad.tudee.ui.composable.delete_item

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.baghdad.tudee.R
import com.baghdad.tudee.ui.composable.TudeeBottomSheet

@Composable
fun DeleteTaskBottomSheet(
    onDeleteClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    DeleteItemContent(
        title = stringResource(id = R.string.delete_task),
        message = stringResource(R.string.continue_text),
        deleteButtonText = stringResource(R.string.delete),
        cancelButtonText = stringResource(R.string.cancel),
        onDeleteClick = onDeleteClick,
        onCancelClick = onCancelClick,
    )
}

@Composable
fun ShowDeleteTaskSheet(
    isVisible: Boolean,
    onDeleteConfirmed: () -> Unit = {},
    onCancelConfirmed: () -> Unit = {},
) {

    var showSheet by remember { mutableStateOf(false) }
        TudeeBottomSheet(
            isVisible = showSheet,
            onDismiss = { showSheet = false }
        ) {
            DeleteTaskBottomSheet(
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
    TudeeBottomSheet(
        isVisible = isVisible,
        onDismiss = { onCancelConfirmed() }
    ) {
        DeleteTaskBottomSheet(
            isLoading = isLoading,
            onDeleteClick = { onDeleteConfirmed() },
            onCancelClick = { onCancelConfirmed() }
        )
    }
}
