package com.baghdad.tudee.ui.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.designSystem.theme.TudeeTheme

@Composable
fun Slider(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    @DrawableRes icon: Int,
    @DrawableRes image: Int,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BasicText(
                    text = title,
                    style = Theme.typography.title.small.copy(
                        color = Theme.color.textColor.title.copy(alpha = 0.87f)
                    )
                )
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
            BasicText(
                text = description,
                style = Theme.typography.body.small.copy(
                    color = Theme.color.textColor.body.copy(alpha = 0.6f)
                )
            )

        }
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            modifier = Modifier
                .height(92.dp)
        )
    }
}

@Preview
@Composable
private fun SliderPreview() {
    TudeeTheme {
        Slider(
            title = "Start working!",
            description = "You've completed 3 out of 10 tasks \n Keep going!",
            icon = com.baghdad.tudee.R.drawable.ic_okay_feedback,
            image = com.baghdad.tudee.R.drawable.happy_robot,
            modifier = Modifier.background(Color.White)
        )
    }
}