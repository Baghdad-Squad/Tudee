package com.baghdad.tudee.ui.screens.tasks

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.ui.utils.noRippleClickable


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TasksHeader(
    month: String,
    year: String,
    onDownArrowClicked: () -> Unit,
    onNextArrowClicked: () -> Unit,
    onPreviousArrowClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavigationIcon(
            icon = painterResource(id = R.drawable.ic_left_arrow),
            contentDescription = stringResource(R.string.left_arrow),
            modifier = Modifier.noRippleClickable { onPreviousArrowClicked() }
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AnimatedContent(
                targetState = "$month, $year",
                transitionSpec = {
                    fadeIn() with fadeOut()
                }
            ) { animatedText ->
                Text(
                    text = animatedText,
                    style = Theme.typography.label.medium,
                    color = Theme.color.textColor.body,
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.ic_right_arrow),
                contentDescription = stringResource(R.string.arrow_down),
                tint = Theme.color.textColor.body,
                modifier = Modifier
                    .rotate(90f)
                    .noRippleClickable { onDownArrowClicked() }
            )
        }
        NavigationIcon(
            icon = painterResource(id = R.drawable.ic_right_arrow),
            contentDescription = stringResource(R.string.right_arrow),
            modifier = Modifier.noRippleClickable { onNextArrowClicked() }
        )
    }
}

@Composable
fun NavigationIcon(
    icon: Painter,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .border(
                width = 1.dp,
                color = Theme.color.textColor.stroke,
                shape = CircleShape
            )
    ) {
        Icon(
            painter = icon,
            contentDescription = contentDescription,
            tint = Theme.color.textColor.body,
            modifier = Modifier.padding(8.dp)
        )
    }
}