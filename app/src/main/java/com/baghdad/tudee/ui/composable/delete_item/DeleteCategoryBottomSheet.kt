package com.baghdad.tudee.ui.composable.delete_item

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.baghdad.tudee.R
import com.baghdad.tudee.ui.composable.TudeeBottomSheet

@Composable
fun DeleteCategoryBottomSheet(
    onDeleteClick: () -> Unit,
    onCancelClick: () -> Unit,
    isLoading: Boolean = false
) {
    DeleteItemContent(
        title ="Delete category",
        message = stringResource(R.string.continue_text),
        deleteButtonText = stringResource(R.string.delete),
        cancelButtonText = stringResource(R.string.cancel),
        onDeleteClick = onDeleteClick,
        onCancelClick = onCancelClick,
        isLoading = isLoading
    )
}


@Composable
fun ShowDeleteCategorySheet(
    modifier: Modifier = Modifier,
    onDeleteConfirmed: () -> Unit = {},
    onCancelConfirmed: () -> Unit = {},
    isLoading: Boolean = false,
) {
    var showSheet by remember { mutableStateOf(false) }
    TudeeBottomSheet(
        isVisible = showSheet,
        onDismiss = { showSheet = false }
    ) {
        DeleteCategoryBottomSheet(
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

