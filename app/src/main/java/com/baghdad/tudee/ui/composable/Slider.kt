package com.baghdad.tudee.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.designSystem.theme.TudeeTheme

@Composable
fun Slider(
    title: String,
    description: String,
     icon: Painter,
     image: Painter,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        SliderContent(
            title = title,
            description = description,
            icon = icon,
            image = image
        )
    }

}

@Composable
fun SliderContent(
    title: String,
    description: String,
    icon: Painter,
    image: Painter,

    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BasicText(
                text = title,
                style = Theme.typography.title.small.copy(
                    color = Theme.color.textColor.title
                )
            )
            StatusSlider(
                icon = icon,
            )

        }
        BasicText(
            text = description,
            style = Theme.typography.body.small.copy(
                color = Theme.color.textColor.body
            )
        )
    }
    SliderIllustration(
        image = image,
        title = title
    )

}


@Composable
fun SliderIllustration(
    title: String,
    image: Painter,
    modifier: Modifier = Modifier
) {
    Image(
        painter = image,
        contentDescription = title,
        modifier = modifier
            .height(92.dp)
    )
}

@Preview
@Composable
private fun SliderPreview() {
    TudeeTheme {
        Slider(
            title = "Start working!",
            description = "You've completed 3 out of 10 tasks \n Keep going!",
            icon = painterResource(R.drawable.ic_good_feedback),
            image = painterResource(R.drawable.happy_robot),
            modifier = Modifier.background(Color.White)
        )
    }
}