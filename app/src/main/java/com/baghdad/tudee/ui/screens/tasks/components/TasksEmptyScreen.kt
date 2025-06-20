package com.baghdad.tudee.ui.screens.tasks.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme


@Composable
fun TasksEmptyScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
//            .offset(y = -(68).dp)
            .padding(start = 12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .zIndex(999f)
                ) {
                    TasksEmptyScreenTextBox(
                        modifier = Modifier
                            .offset(y = -(40).dp, x = (24).dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .zIndex(-1f)
                ) {

                    TasksEmptyScreenIllustration()
                }
            }
        }
    }

}

@Composable
fun TasksEmptyScreenTextBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
//            .offset(y = (-40).dp)
            .clip(
                RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomEnd = 2.dp,
                    bottomStart = 16.dp
                )
            )
            .background(Theme.color.surfaceColor.surfaceHigh)
            .padding(start = 12.dp, end = 8.dp)
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
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

@Composable
private fun TasksEmptyScreenIllustration(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(end = 20.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.no_tasks_image_container),
            contentDescription = stringResource(R.string.image_container),
            modifier = Modifier.size(168.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.no_tasks_overlay),
            contentDescription = stringResource(R.string.no_tasks_overlay_image),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(152.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.progress_indicator),
            contentDescription = stringResource(R.string.progress_indicator),
            modifier = Modifier.align(Alignment.CenterStart)

        )
        Image(
            painter = painterResource(id = R.drawable.no_tasks_tudee),
            contentDescription = stringResource(R.string.no_tasks_robot),
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}