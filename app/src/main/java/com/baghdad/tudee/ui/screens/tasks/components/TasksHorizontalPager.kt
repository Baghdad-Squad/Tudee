package com.baghdad.tudee.ui.screens.tasks.components

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.screens.tasks.TasksUiState

@Composable
fun TasksHorizontalPager(
    uiState: TasksUiState,
    onTaskClick: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    HorizontalPager(
        state = pagerState,
    ) { currentPage ->
        when(currentPage){
            0 -> {
                TasksList(
                    tasks = uiState.inProgressTasks,
                    categories = uiState.categories,
                    onTaskDelete = onDeleteTask,
                    onTaskClick = onTaskClick,
                    modifier = modifier
                )
            }
            1 -> {
                TasksList(
                    tasks = uiState.todoTasks,
                    categories = uiState.categories,
                    onTaskDelete = onDeleteTask,
                    onTaskClick = onTaskClick,
                    modifier = modifier
                )
            }
            2 -> {
                TasksList(
                    tasks = uiState.doneTasks,
                    categories = uiState.categories,
                    onTaskDelete = onDeleteTask,
                    onTaskClick = onTaskClick,
                    modifier = modifier
                )
            }
        }
    }
}