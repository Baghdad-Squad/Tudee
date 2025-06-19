package com.baghdad.tudee.ui.composable.delete

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.ui.composable.TudeeBottomSheet
import com.baghdad.tudee.ui.composable.button.NegativeButton
import com.baghdad.tudee.ui.composable.button.SecondaryButton

@Composable
fun DeleteCategoryContent(
    title: String = "Delete category",
    message: String = "Are you sure to continue?",
    deleteButtonText: String = "Delete",
    cancelButtonText: String = "Cancel",
    onDeleteClick: () -> Unit,
    onCancelClick: () -> Unit,
    isLoading: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(378.dp)
            .background(
                color = Theme.color.surfaceColor.surfaceLow,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(top = 24.dp)
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = title,
                style = Theme.typography.title.large,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = message,
                style = Theme.typography.body.medium,
                color = Theme.color.textColor.body,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(12.dp))

            Image(
                painter = painterResource(R.drawable.img_sad_robot),
                contentDescription = "Sad Robot",
                modifier = Modifier
                    .width(107.dp)
                    .height(108.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(148.dp)
                    .background(
                        color = Theme.color.surfaceColor.surfaceHigh,
                        shape = RoundedCornerShape(
                            bottomStart = 24.dp,
                            bottomEnd = 24.dp
                        )
                    )
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 12.dp,
                        bottom = 12.dp
                    )
            ) {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    NegativeButton(
                        label = deleteButtonText,
                        onClick = onDeleteClick,
                        isLoading = isLoading,
                        isEnabled = !isLoading,
                        modifier = Modifier
                            .width(328.dp)
                            .height(56.dp)
                            .clip(RoundedCornerShape(100.dp))
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    SecondaryButton(
                        label = cancelButtonText,
                        onClick = onCancelClick,
                        modifier = Modifier
                            .width(328.dp)
                            .height(56.dp)
                            .clip(RoundedCornerShape(100.dp))
                    )
                }
            }
        }
    }
}
@Composable
fun DeleteCategoryBottomSheet(
    onDeleteClick: () -> Unit,
    onCancelClick: () -> Unit,
    title: String = "Delete category",
    message: String = "Are you sure to continue?",
    deleteButtonText: String = "Delete",
    cancelButtonText: String = "Cancel",
    isLoading: Boolean = false
) {
    DeleteCategoryContent(
        title = title,
        message = message,
        deleteButtonText = deleteButtonText,
        cancelButtonText = cancelButtonText,
        onDeleteClick = onDeleteClick,
        onCancelClick = onCancelClick,
        isLoading = isLoading
    )
}
@Composable
fun ShowDeleteCategorySheetButton(
    buttonLabel: String = "Delete Category",
    title: String = "Delete category",
    message: String = "Are you sure to continue?",
    deleteButtonText: String = "Delete",
    cancelButtonText: String = "Cancel",
    onDeleteConfirmed: () -> Unit = {},
    onCancelConfirmed: () -> Unit = {},
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    var showSheet by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {

        NegativeButton(
            onClick = { showSheet = true },
            label = buttonLabel,
            modifier = Modifier.align(Alignment.Center)
        )
        TudeeBottomSheet(
            isVisible = showSheet,
            onDismiss = { showSheet = false }
        ) {
            DeleteCategoryBottomSheet(
                title = title,
                message = message,
                deleteButtonText = deleteButtonText,
                cancelButtonText = cancelButtonText,
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
fun DeleteCategoryPreview() {
    DeleteCategoryBottomSheet(
        onDeleteClick = {  },
        onCancelClick = {  }
    )
}
@Preview(showBackground = true)
@Composable
fun ShowDeleteCategoryPreview() {
    ShowDeleteCategorySheetButton()
}
