package com.baghdad.tudee.ui.composable.bottomSheet.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.ui.composable.TudeeBottomSheet
import com.baghdad.tudee.ui.composable.TudeeTextField
import com.baghdad.tudee.ui.composable.button.NegativeTextButton
import com.baghdad.tudee.ui.utils.getCategoryIconPainter

@Composable
fun AddEditCategoryBottomSheet(
    state: AddEditCategorySheetUiState,
    onCategoryTitleChanged: (String) -> Unit,
    onUploadIconClicked: () -> Unit,
    onDeleteClick: (() -> Unit)? = null,
    onSaveClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    TudeeBottomSheet(
        isVisible = state.isVisible,
        onDismiss = onDismiss,
    ) {
        val (titleStringResource, actionStringResource) = remember {
            if (state.isEditing) {
                R.string.edit_category to R.string.save
            } else {
                R.string.add_new_category to R.string.add
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .background(color = Theme.color.surfaceColor.surface)
        ) {
            Row(
                modifier = Modifier.padding(bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(titleStringResource),
                    style = Theme.typography.title.large,
                    color = Theme.color.textColor.title,
                    modifier = Modifier.weight(1f)
                )

                if (state.isEditing) {
                    NegativeTextButton(
                        label = stringResource(R.string.delete),
                        onClick = { onDeleteClick?.invoke() }
                    )
                }
            }

            TudeeTextField(
                value = state.categoryTitle,
                hint = stringResource(R.string.category_title),
                onValueChange = onCategoryTitleChanged,
                leadingIcon = painterResource(id = R.drawable.ic_menu_circle),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Text(
                text = stringResource(R.string.category_image),
                style = Theme.typography.title.large,
                color = Theme.color.textColor.title
            )

            UploadedImageBox(
                isImageUploaded = state.isCategoryImageUploaded,
                onUploadImageClicked = onUploadIconClicked,
                image = state.categoryImageByteArray?.let { byteArray ->
                    getCategoryIconPainter(Category.Image.ByteArray(byteArray))
                },
                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
            )
        }

        ConfirmationButtonContainer(
            isEnabled = state.isActionButtonEnabled,
            onActionClick = onSaveClick,
            onCancelClick = onDismiss,
            actionLabel = stringResource(actionStringResource),
            isLoading = state.isLoading
        )
    }
}