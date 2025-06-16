package com.baghdad.tudee.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.textStyle.tudeeTextStyle
import com.baghdad.tudee.designSystem.theme.Theme

@Composable
fun TudeeHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .offset(8.dp, y = 25.dp)
            .fillMaxWidth()
            .background(
                color = Theme.color.primaryColor.normal,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Tudee avatar",
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Column {
            Text(
                text = "Tudee",
                color = Theme.color.primaryColor.variant,
                style = tudeeTextStyle.logo
            )
            Text(
                text = "Your cute Helper for Every Task",
                color = Theme.color.textColor.onPrimary.copy(alpha = 0.9f),
                style = tudeeTextStyle.label.small
            )
        }
    }
}

@Composable
fun CircularIconButton(
    onClick: () -> Unit,
    icon: Painter,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    backgroundColor: Color = Theme.color.textColor.onPrimary,
    iconTintColor: Color = Theme.color.status.emojiTint
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .background(color = backgroundColor, shape = CircleShape)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back_button),
            contentDescription = contentDescription,
            tint = iconTintColor
        )
    }
}


@Composable
fun TasksAppBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .offset(y = 20.dp)
            .fillMaxWidth()
            .height(64.dp)
            .background(Theme.color.surfaceColor.surfaceHigh)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularIconButton(
            onClick = { /* Handle back button click */ },
            icon = painterResource(id = R.drawable.ic_back_button),
            contentDescription = "Back",
            modifier = Modifier.size(40.dp),
            iconTintColor = Theme.color.status.emojiTint
        )
        Text(
            modifier = Modifier,
            text = "Tasks",
            style = MaterialTheme.typography.titleLarge,
            color = Theme.color.status.emojiTint
        )
    }
}

@Composable
fun CombinedScreen() {
    Column(
        modifier = Modifier
            .size(400.dp, 400.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TudeeHeader(modifier = Modifier.padding(16.dp))
        TasksAppBar(modifier = Modifier.padding(horizontal = 16.dp))
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CombinedScreenPreview() {
    MaterialTheme {
        CombinedScreen()
    }
}