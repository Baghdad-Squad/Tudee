package com.baghdad.tudee.ui.screens.tasks.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.composable.TasksEmptyScreen
import com.baghdad.tudee.ui.utils.getCategoryIconPainter

@Composable
fun TasksList(
    tasks: List<Task>,
    categories: List<Category>,
    onTaskDelete: (Task) -> Unit,
    onTaskClick: (Task) -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        modifier = Modifier.fillMaxSize(),
        targetState = tasks.isEmpty(),
    ) { isEmpty ->
        if(isEmpty){
            TasksEmptyScreen()
        } else {
            LazyColumn(
                modifier = modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 40.dp),
                contentPadding = PaddingValues(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    tasks,
                    key = { it.id }
                ) { task ->
                    val category = categories.first { it.id == task.categoryId }
                    SwipeToDeleteCard(
                        title = task.title,
                        description = task.description,
                        priorityTask = task.priority,
                        icon = getCategoryIconPainter(category.image),
                        onDelete = { onTaskDelete(task) },
                        onClick = { onTaskClick(task) },
                    )
                }
            }
        }
    }
}