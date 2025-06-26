package com.baghdad.tudee.ui.shared.components.category

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.ui.utils.dashedBorder
import com.baghdad.tudee.ui.utils.noRippleClickable

@Composable
fun UploadedImageBox(
    isImageUploaded: Boolean,
    onUploadImageClicked: () -> Unit,
    modifier: Modifier = Modifier,
    image: Painter? = null
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onUploadImageClicked() }
            .dashedBorder(
                width = 1.dp,
                color = Theme.color.textColor.stroke,
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = isImageUploaded
        ) { isUploaded ->
            if (isUploaded) {
                UploadedImage(
                    image = image,
                    onUploadImageClicked = onUploadImageClicked
                )
            } else {
                UploadPlaceholder(onUploadClick = onUploadImageClicked)
            }
        }
    }
}

@Composable
fun UploadedImage(
    image: Painter?,
    onUploadImageClicked: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = image ?: painterResource(id = R.drawable.ic_add_image),
            contentDescription = stringResource(R.string.uploaded_image),
            modifier = Modifier
                .size(112.dp),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.colorMatrix(
                ColorMatrix().apply {
                    setToScale(0.8f, 0.8f, 0.8f, 1f)
                }
            )
        )
        Icon(
            painter = painterResource(id = R.drawable.pencil_edit_01),
            contentDescription = stringResource(R.string.edit_icon),
            tint = Theme.color.secondaryColor,
            modifier = Modifier
                .size(32.dp)
                .noRippleClickable(onUploadImageClicked)
                .background(
                    color = Theme.color.surfaceColor.surfaceHigh,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(6.dp)
        )
    }
}


@Composable
private fun UploadPlaceholder(
    onUploadClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .noRippleClickable(onUploadClick)
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add_image),
                contentDescription = stringResource(R.string.edit_icon),
                tint = Theme.color.textColor.hint,
                modifier = Modifier

            )
            Text(
                text = stringResource(R.string.upload),
                style = Theme.typography.label.medium,
                color = Theme.color.textColor.hint
            )
        }
    }
}

