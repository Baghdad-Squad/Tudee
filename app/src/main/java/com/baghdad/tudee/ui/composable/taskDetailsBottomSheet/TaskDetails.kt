package com.baghdad.tudee.ui.composable.taskDetailsBottomSheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.composable.TaskPriority
import com.baghdad.tudee.ui.composable.TudeeBottomSheet
import com.baghdad.tudee.ui.composable.button.PrimaryButton
@Composable
fun TaskDetails(
    isVisible: Boolean,
    onDismissRequest: () -> Unit,
    icon: Painter,
    priorityTask: Task.Priority,
    taskState: Task.State,

    ) {
    TudeeBottomSheet(
        isVisible = isVisible,
        onDismiss = onDismissRequest,
    ) {
        TaskDetailsContent(
            priorityTask = priorityTask,
            icon = icon,
            taskState = taskState,
            description = "Solve all exercises from page 45 to 50 in the \ntextbook, Solve all exercises from page 45 to 50 in \nthe textbook.",
            title = "Organize Study Desk"
        )
    }

}

@Composable
fun TaskDetailsContent(
    icon: Painter,
    priorityTask: Task.Priority,
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
            text = "Task details",
            style = Theme.typography.title.large,
            color = Theme.color.textColor.title
        )
        Spacer(modifier = Modifier.height(12.dp))
        Box {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(color = Theme.color.surfaceColor.surfaceHigh, shape = CircleShape)
            ) {
                Image(
                    painter = icon,
                    contentDescription = "label",
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.Center)
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text =title ,
            style = Theme.typography.title.medium,
            color = Theme.color.textColor.title
        )
        Text(
            text =description ,
            style = Theme.typography.body.small,
            color = Theme.color.textColor.body
        )
        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(
                    color = Theme.color.textColor.stroke,
                    shape = CircleShape
                )
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row() {
            TaskState(taskState = taskState)
            Spacer(modifier = Modifier.width(8.dp))
            TaskPriority(priorityTask = priorityTask)
        }


        if (taskState != Task.State.DONE) {
            Spacer(modifier = Modifier.height(24.dp))
            val buttonText = when (taskState) {
                Task.State.TODO -> "Move to in progress"
                Task.State.IN_PROGRESS -> "Move to done"
                else -> ""
            }

            TaskActionsContainer(buttonText = buttonText)        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskDetailsPreview() {
}