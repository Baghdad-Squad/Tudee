package com.baghdad.tudee.designSystem.components.delete_item

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.baghdad.tudee.R
import com.baghdad.tudee.ui.shared.components.TudeeBottomSheet

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
    isVisible: Boolean,
    onDeleteConfirmed: () -> Unit = {},
    onCancelConfirmed: () -> Unit = {},
    isLoading: Boolean = false,
) {
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
