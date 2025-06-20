package com.baghdad.tudee.ui.composable.delete

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
fun DeleteItemContent(
    title: String ,
    message: String ,
    deleteButtonText: String ,
    cancelButtonText: String ,
    onDeleteClick: () -> Unit,
    onCancelClick: () -> Unit,
    isLoading: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
            .height(378.dp)
            .background(
                color = Theme.color.surfaceColor.surfaceLow,
                shape = RoundedCornerShape(24.dp)
            )

    ) {
        DeleteItemBottomSheetHeader(title, message)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .background(color = Theme.color.surfaceColor.surfaceHigh)
                    .padding( vertical = 12.dp, horizontal = 16.dp)
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
fun ShowDeleteTaskSheetButton(
    onDeleteConfirmed: () -> Unit = {},
    onCancelConfirmed: () -> Unit = {},
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    var showSheet by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        
        NegativeButton(
            onClick = { showSheet = true },
            label = stringResource(id =  R.string.delete_task),
            modifier = Modifier.align(Alignment.Center)
        )

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
}

@Preview(showBackground = true)
@Composable
fun DeleteTaskPreview() {
    DeleteTaskBottomSheet(
        onDeleteClick = {  },
        onCancelClick = {  }
    )
}

@Preview(showBackground = true)
@Composable
fun ShowDeleteTaskPreview() {
    ShowDeleteTaskSheetButton()
}