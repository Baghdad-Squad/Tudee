package com.baghdad.tudee.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
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
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.designSystem.theme.TudeeTheme

@Composable
fun SnakeBar(
    modifier: Modifier = Modifier,
    message: String,
    isSuccess: Boolean,
    isVisible: Boolean = false,
) {

    AnimatedVisibility(
        isVisible,
        enter = fadeIn(tween(500)) + slideIn(){ IntOffset(it.width, 0) },
        exit = fadeOut(tween(500)) + slideOut(
            targetOffset = { IntOffset(it.width / 2 , 0)  },
            animationSpec = tween(500)
        )
        ) {
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
    val backgroundColor = getStatusBackgroundColor(isSuccess)
    val painterIcon = getStatusIconPainter(isSuccess)
    val tint = getStatusTint(isSuccess)
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(color = backgroundColor.value,
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material3.Icon(
            painter =painterIcon,
            contentDescription = null,
            tint = tint.value
        )
    }
}

@Composable
private fun getStatusBackgroundColor(isSuccess: Boolean): State<Color> {
    return animateColorAsState(
        targetValue = if (isSuccess) Theme.color.status.greenVariant
        else Theme.color.status.errorVariant,
        animationSpec = tween(durationMillis = 300)
    )
}

@Composable
private fun getStatusIconPainter(isSuccess: Boolean): Painter {
    return if (isSuccess) {
        painterResource(id = R.drawable.ic_checkmark_badge)
    } else {
        painterResource(id = R.drawable.ic_information_diamond)
    }
}

@Composable
private fun getStatusTint(isSuccess: Boolean): State<Color> {
    return animateColorAsState(
        targetValue = if (isSuccess) Theme.color.status.greenAccent
        else Theme.color.status.error,
        animationSpec = tween(durationMillis = 300)
    )
}

@Composable
@Preview
private fun SnakeBarPreview() {
    TudeeTheme {
        SnakeBar(
            message = "Successfully",
            isSuccess = true,
            isVisible = true
        )

    }
}