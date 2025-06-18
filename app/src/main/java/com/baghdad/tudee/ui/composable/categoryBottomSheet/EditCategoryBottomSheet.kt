package com.baghdad.tudee.ui.composable.categoryBottomSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.ui.composable.TudeeBottomSheet
import com.baghdad.tudee.ui.composable.TudeeTextField
import com.baghdad.tudee.ui.utils.dashedBorder


@Composable
fun EditCategoryBottomSheet(
    isVisible: Boolean,
    onDismissRequest: () -> Unit,
    title: String,
    onValueChange: (String) -> Unit,
    onEditImageIconClick: () -> Unit,
    onAddButtonClick: () -> Unit,
    onCanceleButtonClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    TudeeBottomSheet(
        isVisible = isVisible,
        onDismiss = onDismissRequest,
    ) {
        EditCategoryBottomSheetContent(
            title = title,
            onValueChange = onValueChange,
            onEditImageIconClick = onEditImageIconClick,
            onAddButtonClick = onAddButtonClick,
            onCanceleButtonClick = onCanceleButtonClick,
            onDeleteClick = onDeleteClick
        )
    }

}

@Composable
fun EditCategoryBottomSheetContent(
    title: String,
    onValueChange: (String) -> Unit,
    onDeleteClick: () -> Unit,
    onEditImageIconClick: () -> Unit,
    onAddButtonClick: () -> Unit,
    onCanceleButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .background(color = Theme.color.surfaceColor.surface)
        ) {
            Row {
                Text(
                    text = "Edit category",
                    style = Theme.typography.title.large,
                    color = Theme.color.textColor.title
                )
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    modifier = Modifier.clickable {
                        onDeleteClick()
                    },
                    text = "Delete",
                    style = Theme.typography.title.large,
                    color = Theme.color.status.error
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            TudeeTextField(
                value = title,
                hint = "Category title",
                onValueChange = onValueChange,
                leadingIcon = painterResource(id = R.drawable.ic_menu_circle)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Category image",
                style = Theme.typography.title.large,
                color = Theme.color.textColor.title
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .dashedBorder(
                        width = 1.dp,
                        color = Theme.color.textColor.stroke,
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                UploadedImageBox(onEditClick = onEditImageIconClick)
            }
            Spacer(modifier = Modifier.height(24.dp))

        }
        ConfirmationButtonContainer(
            showButton = true,
            onAddClick = onAddButtonClick,
            onCancelClick = onCanceleButtonClick,
            PrimaryButtonLable = "Save"
        )

    }
}
