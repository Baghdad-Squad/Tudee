package com.baghdad.tudee.ui.screens.homeScreen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme

@Composable
fun NoTaskMessage(modifier: Modifier = Modifier) {
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    Box(
        modifier = modifier
            .height(160.dp)
            .fillMaxWidth()

    ) {
        Box(
            Modifier
                .width(360.dp)
                .align(Alignment.TopEnd)
        ) {
            Box(
                modifier = Modifier
                    .offset(y = (-12).dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomEnd = 2.dp,
                            bottomStart = 16.dp
                        )
                    )
                    .background(Theme.color.surfaceColor.surfaceHigh)
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .align(Alignment.TopStart),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.no_tasks_yet),
                        style = Theme.typography.title.small,
                        color = Theme.color.textColor.body
                    )
                    Text(
                        text = stringResource(R.string.empty_tasks_desc),
                        style = Theme.typography.body.small,
                        color = Theme.color.textColor.hint
                    )
                }
            }
        }
        Box(
            modifier = modifier
                .align(Alignment.BottomEnd)
        ) {
            Image(
                painter = painterResource(R.drawable.no_tasks_image_container),
                contentDescription = stringResource(R.string.image_container),
                modifier = Modifier
                    .size(144.dp)
                    .offset(x = (-5).dp)
                    .graphicsLayer {
                        scaleX = if (isRtl) -1f else 1f
                    }
            )

            Image(
                painter = painterResource(R.drawable.no_tasks_overlay),
                contentDescription = stringResource(R.string.no_tasks_overlay_image),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(136.dp)
                    .offset(y = 3.dp)

            )

            Icon(
                painter = painterResource(R.drawable.progress_indicator),
                contentDescription = stringResource(R.string.progress_indicator),
                tint = Theme.color.surfaceColor.surfaceHigh,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(top = 20.dp)
                    .graphicsLayer {
                        scaleX = if (isRtl) -1f else 1f
                    }
            )

            Image(
                painter = painterResource(R.drawable.no_tasks_tudee),
                contentDescription = stringResource(R.string.no_tasks_robot),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(start = 10.dp)

            )
        }
    }
}