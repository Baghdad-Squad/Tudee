package com.baghdad.tudee.ui.composable.delete_item

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.baghdad.tudee.R
import com.baghdad.tudee.ui.composable.TudeeBottomSheet

@Composable
fun DeleteCategoryBottomSheet(
    isVisible: Boolean,
    onDeleteClick: () -> Unit,
    onDismiss: () -> Unit,

) {
    TudeeBottomSheet(
        isVisible = isVisible,
        onDismiss = onDismiss
    ) {
        DeleteItemContent(
            title = "Delete category",
            message = stringResource(R.string.continue_text),
            deleteButtonText = stringResource(R.string.delete),
            cancelButtonText = stringResource(R.string.cancel),
            onDeleteClick = onDeleteClick,
            onCancelClick = onDismiss,

        )

    }

}
