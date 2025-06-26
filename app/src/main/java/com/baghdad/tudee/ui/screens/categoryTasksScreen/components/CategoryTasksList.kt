package com.baghdad.tudee.ui.screens.categoryTasksScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.composable.CategoryTaskCard
import com.baghdad.tudee.ui.screens.categoryTasksScreen.CategoryTasksScreenUiState
import com.baghdad.tudee.ui.screens.homeScreen.addEditTask.getCategoryIconPainter

@Composable
fun CategoryTasksList(
    tasks: List<Task>,
    state: CategoryTasksScreenUiState,
    modifier: Modifier = Modifier) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(tasks) { task ->
            CategoryTaskCard(
                title = task.title,
                description = task.description,
                priorityTask = task.priority,
                icon = getCategoryIconPainter(categoryImage = state.category.image),
                onClick = { /*TODO: Show task details bottom sheet*/ },
                date = task.date.toString(),
                showDate = true
            )
        }
    }
}