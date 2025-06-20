package com.baghdad.tudee.ui.composable.categoryBottomSheet

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.ui.composable.TudeeBottomSheet
import com.baghdad.tudee.ui.composable.TudeeTextField
import com.baghdad.tudee.ui.utils.dashedBorder

@Composable
fun AddCategoryBottomSheet(
    isVisible: Boolean,
    isLoading: Boolean,
    onDismiss: () -> Unit,
    title: String,
    onCategoryTitleChanged: (String) -> Unit,
    isAddButtonEnabled: Boolean,
    isCategoryImageUploaded: Boolean,
    onUploadIconClicked: () -> Unit,
    onEditImageIconClick: () -> Unit,
    onAddButtonClick: () -> Unit,
    onCancelButtonClick: () -> Unit,
    image : Painter? = null,
    ) {
    TudeeBottomSheet(
        isVisible = isVisible,
        onDismiss = onDismiss,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item() {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .background(color = Theme.color.surfaceColor.surface)
                ) {
                    Text(
                        text = stringResource(R.string.add_new_category),
                        style = Theme.typography.title.large,
                        color = Theme.color.textColor.title,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    TudeeTextField(
                        value = title,
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
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .padding(bottom = 24.dp)
                            .dashedBorder(
                                width = 1.dp,
                                color = Theme.color.textColor.stroke,
                                shape = RoundedCornerShape(16.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        AnimatedContent(
                            targetState = isCategoryImageUploaded,
                            label = "UploadStateAnimation"
                        ) { isUploaded ->
                            if (isUploaded) {
                                UploadedImageBox(onEditClick = onEditImageIconClick, image)
                            } else {
                                UploadPlaceholder(onUploadClick = onUploadIconClicked)
                            }
                        }

                    }
                }
                ConfirmationButtonContainer(
                    isEnabled = isAddButtonEnabled,
                    onAddClick = onAddButtonClick,
                    onCancelClick = onCancelButtonClick,
                    actionLabel = stringResource(R.string.add),
                    isLoading = isLoading
                )

            }
        }
    }
}




