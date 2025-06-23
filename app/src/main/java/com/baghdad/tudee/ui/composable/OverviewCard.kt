package com.baghdad.tudee.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.ui.screens.homeScreen.TaskState
import com.baghdad.tudee.ui.utils.insideBorder

@Composable
fun OverviewCard(
    count: Int,
    background: Color,
    taskState: TaskState,
    modifier: Modifier = Modifier,
) {


    Box(
        modifier = modifier
            .zIndex(999f)
            .height(112.dp)
            .width(96.dp)
            .background(background, shape = RoundedCornerShape(20.dp))
    ) {
        Column(modifier
            .padding(12.dp)
            .background(Color.Transparent, shape = RoundedCornerShape(20.dp))) {
            Box(
                modifier
                    .
                    size(40.dp)
                    .background(
                        color = Color(0x3DFFFFFF),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .insideBorder(1.dp, Color(0x1FFFFFFF), 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(
                        when (taskState) {
                            TaskState.TODO -> R.drawable.ic_overview_card_todo
                            TaskState.IN_PROGRESS -> R.drawable.ic_overview_card_in_progress
                            TaskState.DONE -> R.drawable.ic_overview_card_done
                        }
                    ), contentDescription = when (taskState) {
                        TaskState.TODO -> stringResource(R.string.to_do)
                        TaskState.IN_PROGRESS -> stringResource(R.string.in_progress)
                        TaskState.DONE -> stringResource(R.string.done)
                    },
                    tint = Theme.color.textColor.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }
            Text(
                text = count.toString(),
                style = Theme.typography.headline.medium,
                color = Theme.color.textColor.onPrimary,
            )
            Text(
                text = when (taskState) {
                    TaskState.TODO -> stringResource(R.string.to_do)
                    TaskState.IN_PROGRESS -> stringResource(R.string.in_progress)
                    TaskState.DONE -> stringResource(R.string.done)
                },
                style = Theme.typography.label.small,
                color = Theme.color.textColor.onPrimaryCaption,
            )


        }

        Icon(
            painter = painterResource(R.drawable.overview_card_background),
            contentDescription = "Overview Card Background",
            modifier = Modifier
                .clip(RoundedCornerShape(topEnd = 20.dp))
                .align(Alignment.TopEnd)
            ,
            tint = Color.Unspecified
        )

    }
}