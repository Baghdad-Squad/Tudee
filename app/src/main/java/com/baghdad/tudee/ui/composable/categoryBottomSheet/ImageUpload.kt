package com.baghdad.tudee.ui.composable.categoryBottomSheet

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.ui.utils.dashedBorder

@Composable
fun UploadedImageBox(
    onEditClick: () -> Unit,
    image : Painter?
) {
    Box(
        modifier = Modifier
            .size(112.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onEditClick() }
    ) {
        Image(
            painter = image ?: painterResource(id = R.drawable.image_add_02),
            contentDescription = stringResource(R.string.uploaded_image),
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    color = Color(0x1A000000),
                    shape = RoundedCornerShape(16.dp)
                )
                .dashedBorder(
                    width = 1.dp,
                    color = Theme.color.textColor.stroke,
                    shape = RoundedCornerShape(16.dp)
                )
        )

        Box(
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.Center)
                .background(
                    color = Theme.color.surfaceColor.surfaceHigh,
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.pencil_edit_01),
                contentDescription = stringResource(R.string.edit_icon),
                tint = Theme.color.secondaryColor,
                modifier = Modifier
                    .padding(6.dp)
            )
        }
    }
}


@Composable
fun UploadPlaceholder(
    onUploadClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(112.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onUploadClick() }
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.image_add_02),
                contentDescription = stringResource(R.string.edit_icon),
                tint = Theme.color.textColor.hint,
                modifier = Modifier

            )
            Text(
                text = "Upload",
                style = Theme.typography.label.medium,
                color = Theme.color.textColor.hint
            )
        }
    }
}
