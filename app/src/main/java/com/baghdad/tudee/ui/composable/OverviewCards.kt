package com.baghdad.tudee.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.baghdad.tudee.designSystem.theme.Theme.color
import com.baghdad.tudee.ui.screens.homeScreen.HomeScreenUIState
import com.baghdad.tudee.ui.screens.homeScreen.TaskState
import com.baghdad.tudee.ui.utils.insideBorder

@Composable
fun OverviewCards(
    state: HomeScreenUIState,
    modifier: Modifier = Modifier,
) {
    val purpleAccent = color.status.purpleAccent
    val yellowAccent = color.status.yellowAccent
    val greenAccent = color.status.greenAccent

    val overViews = remember {
        listOf(
            OverViewCard(TaskState.TODO, purpleAccent, state.todoTasks.size),
            OverViewCard(
                TaskState.IN_PROGRESS,
                yellowAccent,
                state.inProgressTasks.size
            ),
            OverViewCard(TaskState.DONE, greenAccent, state.doneTasks.size)
        )
    }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        overViews.forEach {


            Box(
                modifier = Modifier
                    .weight(1f)
                    .zIndex(999f)
                    .height(112.dp)
                    .width(96.dp)
                    .background(it.background, shape = RoundedCornerShape(20.dp))
            ) {
                Column(
                    modifier
                        .padding(12.dp)
                        .background(Color.Transparent, shape = RoundedCornerShape(20.dp))
                ) {
                    Box(
                        Modifier
                            .size(40.dp)
                            .background(
                                color = Color(0x3DFFFFFF),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .insideBorder(1.dp, Color(0x1FFFFFFF), 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(it.taskState.toPainterResource()), contentDescription = it.taskState.toStringResource() ,
                            tint = color.textColor.onPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Text(
                        text = it.count.toString(),
                        style = Theme.typography.headline.medium,
                        color = color.textColor.onPrimary,
                    )
                    Text(
                        text = it.taskState.toStringResource(),
                        style = Theme.typography.label.small,
                        color = color.textColor.onPrimaryCaption,
                    )


                }

                Icon(
                    painter = painterResource(R.drawable.overview_card_background),
                    contentDescription = stringResource(R.string.Overview_Card_Background),
                    modifier = Modifier
                        .clip(RoundedCornerShape(topEnd = 20.dp))
                        .align(Alignment.TopEnd),
                    tint = Color.Unspecified
                )

            }
        }
    }
}

private data class OverViewCard(
    val taskState: TaskState,
    val background: Color,
    val count: Int,
)


@Composable
fun TaskState.toStringResource(): String {
    return when (this) {
        TaskState.TODO -> stringResource(R.string.to_do)
        TaskState.IN_PROGRESS -> stringResource(R.string.in_progress)
        TaskState.DONE -> stringResource(R.string.done)
    }
}

@Composable
fun TaskState.toPainterResource(): Int {
    return when (this) {
        TaskState.TODO -> R.drawable.ic_overview_card_todo
        TaskState.IN_PROGRESS -> R.drawable.ic_overview_card_in_progress
        TaskState.DONE -> R.drawable.ic_overview_card_done
    }
}

