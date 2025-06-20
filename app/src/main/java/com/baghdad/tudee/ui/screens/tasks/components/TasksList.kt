package com.baghdad.tudee.ui.screens.tasks.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.screens.tasks.TasksUiState

@Composable
fun TasksList(
    uiState: TasksUiState,
    modifier: Modifier = Modifier,
    onTaskDelete: (Task) -> Unit
) {

    LazyColumn(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 40.dp),
        contentPadding = PaddingValues(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            uiState.tasksDisplayed,
            key = { it.id }
        ) { task ->
            val category = uiState.categories.find { it.id == task.categoryId }
            val painter = painterResource(R.drawable.ic_baseball_bat) /*Add when statements TODO()*/

            SwipeToDeleteCard(
                title = task.title,
                description = task.description,
                priorityTask = task.priority,
                icon = painter,
                onDelete = {  onTaskDelete(task) },
                onClick = {/*TODO()*/ },
            )
        }

    }
}