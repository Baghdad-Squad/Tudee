package com.baghdad.tudee.ui.content.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.composable.TaskPriority

@Composable
fun PriorityButtonPart() {
    Column(modifier = Modifier.fillMaxWidth()
        .padding(vertical = 16.dp,horizontal = 16.dp))
    {
        Text(
            text = "Priority",
            style = Theme.typography.title.medium
        )
        Row(modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)){
            TaskPriority(
                priorityTask = Task.Priority.HIGH,
            )
            TaskPriority(
                priorityTask = Task.Priority.MEDIUM,
            )
            TaskPriority(
                priorityTask = Task.Priority.LOW,
            )
        }
    }
}