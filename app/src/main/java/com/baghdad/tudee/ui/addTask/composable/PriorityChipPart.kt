package com.baghdad.tudee.ui.addTask.composable

import TaskPriority
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.domain.entity.Task

@Composable
fun PriorityChipPart(
    selectedPriority: Task.Priority?,
    onPrioritySelected: (Task.Priority) -> Unit
) {
    val priorities = listOf(
        Task.Priority.HIGH,
        Task.Priority.MEDIUM,
        Task.Priority.LOW
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp)
    ) {
        Text(
            text = "Priority",
            style = Theme.typography.title.medium
        )

        Row(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            priorities.forEach { priority ->
                val isSelected = selectedPriority == priority
                key(priority to isSelected) {
                    TaskPriority(
                        priorityTask = priority,
                        isClickable = true,
                        onClick = { onPrioritySelected(priority) }
                    )
                }
            }
        }
    }
}

