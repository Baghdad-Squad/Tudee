package com.baghdad.tudee.ui.composable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.domain.entity.Task

@Composable
fun SwipeToDeleteCard(
    title: String,
    description: String,
    priorityTask: Task.Priority,
    icon: Painter,
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    onClick: () -> Unit,
) {
    val maxSwipe = 100f
    var offsetX by remember { mutableStateOf(0f) }

    val animatedOffsetX by animateFloatAsState(targetValue = offsetX)

    Box(
        Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(16.dp))
            .background(Theme.color.status.errorVariant)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(end = 24.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = "Delete",
                tint = Theme.color.status.error
            )
        }

        Box(
            Modifier
                .offset(x = animatedOffsetX.dp)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            if (offsetX <= -maxSwipe * 0.7f) {
                                onDelete()
                            } else {
                                offsetX = 0f
                            }
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            val newOffset = offsetX + dragAmount.x
                            offsetX = newOffset.coerceIn(-maxSwipe, 0f)
                        }
                    )
                }
        ) {
            CategoryTaskCard(
                title = title,
                description = description,
                priorityTask = priorityTask,
                icon = icon,
                onClick = onClick,
                modifier = modifier
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SwipeToDeleteCardListPreview() {
    val icon = painterResource(id = R.drawable.ic_book_open)

    var tasks by remember {
        mutableStateOf(
            listOf(
                TaskItem(1, "Task 1", "Description 1", Task.Priority.HIGH, icon),
                TaskItem(2, "Task 2", "Description 2", Task.Priority.MEDIUM, icon),
                TaskItem(3, "Task 3", "Description 3", Task.Priority.LOW, icon)
            )
        )
    }

    LazyColumn {
        items(tasks, key = { it.id }) { task ->
            SwipeToDeleteCard(
                title = task.title,
                description = task.description,
                priorityTask = task.priority,
                icon = task.icon,
                onDelete = {
                    tasks = tasks.filterNot { it.id == task.id }
                },
                onClick = {}
            )
        }
    }
}

data class TaskItem(
    val id: Int,
    val title: String,
    val description: String,
    val priority: Task.Priority,
    val icon: Painter
)