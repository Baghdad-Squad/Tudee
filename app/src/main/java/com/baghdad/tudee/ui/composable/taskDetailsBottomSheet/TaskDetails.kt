package com.baghdad.tudee.ui.composable.taskDetailsBottomSheet

import TaskPriority
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.composable.TudeeBottomSheet
import com.baghdad.tudee.ui.composable.VerticalSeparatorLine

@Composable
fun TaskDetails(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    icon: Painter,
    taskPriority: Task.Priority,
    taskState: Task.State,

    ) {
    TudeeBottomSheet(
        isVisible = isVisible,
        onDismiss = onDismiss,
    ) {
        TaskDetailsContent(
            taskPriority = taskPriority,
            icon = icon,
            taskState = taskState,
            description = stringResource(R.string.taskDescription),
            title = stringResource(R.string.taskTitleExample)
        )
    }

}

@Composable
fun TaskDetailsContent(
    icon: Painter,
    taskPriority: Task.Priority,
    taskState: Task.State,
    title: String,
    description: String


) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 24.dp)
    ) {
        Text(
            text = stringResource(R.string.task_details),
            style = Theme.typography.title.large,
            color = Theme.color.textColor.title,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Box(
            modifier = Modifier
                .size(56.dp)
                .background(color = Theme.color.surfaceColor.surfaceHigh, shape = CircleShape)
                .padding(bottom = 12.dp)
        ) {
            Image(
                painter = icon,
                contentDescription = stringResource(R.string.label),
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.Center)
            )
        }

        Text(
            text = title,
            style = Theme.typography.title.medium,
            color = Theme.color.textColor.title
        )
        Text(
            text = description,
            style = Theme.typography.body.small,
            color = Theme.color.textColor.body,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        VerticalSeparatorLine()
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TaskState(taskState = taskState)
            TaskPriority(priorityTask = taskPriority, isClickable = false)
        }


        if (taskState != Task.State.DONE) {
            Spacer(modifier = Modifier.height(24.dp))
            val buttonText = when (taskState) {
                Task.State.TODO -> stringResource(R.string.move_to_in_progress)
                Task.State.IN_PROGRESS -> stringResource(R.string.move_to_done)
                else -> ""
            }

            TaskActionsContainer(buttonText = buttonText)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskDetailsPreview() {
}