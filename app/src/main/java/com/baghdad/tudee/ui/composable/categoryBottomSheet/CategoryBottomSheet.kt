package com.baghdad.tudee.ui.composable.categoryBottomSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.ui.composable.TudeeBottomSheet
import com.baghdad.tudee.ui.composable.TudeeTextField
import com.baghdad.tudee.ui.composable.button.PrimaryButton
import com.baghdad.tudee.ui.utils.dashedBorder

@Composable
fun CategoryBottomSheet(
    isVisible: Boolean,
    onDismissRequest: () -> Unit,
    title: String,
    onValueChange: (String) -> Unit,
    showButton: Boolean,
    imageUploaded: Boolean,
    onUploadClick: () -> Unit,
    onEditImageIconClick: () -> Unit,
    onAddButtonClick: () -> Unit,
    onCanceleButtonClick: () -> Unit,
) {
    TudeeBottomSheet(
        isVisible = isVisible,
        onDismiss = onDismissRequest,
    ) {
        CategoryBottomSheetContent(
            title = title,
            onValueChange = onValueChange,
            showButton = showButton,
            imageUploaded = imageUploaded,
            onUploadClick = onUploadClick,
            onEditImageIconClick = onEditImageIconClick,
            onAddButtonClick = onAddButtonClick,
            onCanceleButtonClick = onCanceleButtonClick
        )
    }

}

@Composable
fun CategoryBottomSheetContent(
    title: String,
    onValueChange: (String) -> Unit,
    showButton: Boolean,
    imageUploaded: Boolean,
    onUploadClick: () -> Unit,
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
            Text(
                text = "Add new category",
                style = Theme.typography.title.large,
                color = Theme.color.textColor.title
            )
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
                if (imageUploaded) {
                    UploadedImageBox(onEditClick = onEditImageIconClick)
                } else {
                    UploadPlaceholder(onUploadClick = onUploadClick)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

        }
        ConfirmationButtonContainer(
            showButton = showButton,
            onAddClick = onAddButtonClick,
            onCancelClick = onCanceleButtonClick
        )

    }

}



