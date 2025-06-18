package com.baghdad.tudee.ui.screens.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.ui.composable.CategoryTaskCard

@Composable
fun TasksColumn(
    uiState: TasksUiState,
    modifier: Modifier = Modifier
) {


    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp)
            .padding(bottom = 40.dp),
        contentPadding = PaddingValues(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            uiState.tasks
        ) { task ->
            CategoryTaskCard(
                title = task.title,
                description = task.description,
                priorityTask = task.priority,
                icon = painterResource(task.icon),
                onClick = {/*TODO()*/ },
            )
        }
    }
}