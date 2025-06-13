package com.baghdad.tudee.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.designSystem.theme.TudeeTheme

@Composable
fun SnakeBar(
    modifier: Modifier = Modifier,
    message: String,
    isSuccess: Boolean,
    isShown: Boolean = true,
) {
    if(isShown) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Theme.color.surfaceColor.surfaceHigh, shape = RoundedCornerShape(16.dp))
                .padding(start = 8.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BoxIcon(isSuccess = isSuccess)
            Text(
                text = message,
                style = Theme.typography.body.medium,
                color = Theme.color.textColor.body
            )
        }
    }
}

@Composable
private fun BoxIcon(
    isSuccess: Boolean,
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(color =
                if(isSuccess) Theme.color.status.greenVariant else Theme.color.status.errorVariant,
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material3.Icon(
            painter = if (isSuccess) painterResource(id = R.drawable.ic_checkmark_badge) else painterResource(
                id = R.drawable.ic_information_diamond
            ),
            contentDescription = null,
            tint = if (isSuccess) Theme.color.status.greenAccent else Theme.color.status.error
        )
    }
}

@Composable
@Preview
private fun SnakeBarPreview() {
    TudeeTheme {
        SnakeBar(
            message = "Successfully",
            isSuccess = true,
            isShown = true
        )

    }
}