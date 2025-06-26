package com.baghdad.tudee.ui.screens.categoryTasksScreen.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.baghdad.tudee.R
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.composable.TabItem
import com.baghdad.tudee.ui.screens.categoryTasksScreen.CategoryTasksScreenUiState
import com.baghdad.tudee.ui.shared.Selectable


@Composable
 fun rememberTabs(
    state: CategoryTasksScreenUiState,
    context: android.content.Context
): List<Selectable<TabItem>> {
    return remember(state.selectedTab, state.todoTasks, state.inProgressTasks, state.doneTasks) {
        listOf(
            Selectable(
                TabItem(context.getString(R.string.in_progress), state.inProgressTasks.size, Task.State.IN_PROGRESS),
                isSelected = state.selectedTab == Task.State.IN_PROGRESS
            ),
            Selectable(
                TabItem(context.getString(R.string.to_do), state.todoTasks.size, Task.State.TODO),
                isSelected = state.selectedTab == Task.State.TODO
            ),
            Selectable(
                TabItem(context.getString(R.string.done), state.doneTasks.size, Task.State.DONE),
                isSelected = state.selectedTab == Task.State.DONE
            )
        )
    }
}