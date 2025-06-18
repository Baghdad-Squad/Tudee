package com.baghdad.tudee.ui.screens.tasks

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.composable.TabItem
import com.baghdad.tudee.ui.composable.Tabs
import com.baghdad.tudee.ui.shared.Selectable

@Composable
fun StatusTabs(
    uiState: TasksUiState,
    modifier: Modifier = Modifier
) {
    Tabs(
        selectableTabs = listOf(
            Selectable(
                value = TabItem(
                    "In progress",
                    badgeCount = if (uiState.selectedTab == Task.State.IN_PROGRESS) uiState.tasks.size else null
                ),
                isSelected = uiState.selectedTab == Task.State.IN_PROGRESS
            ),
            Selectable(
                value = TabItem(
                    "To Do",
                    badgeCount = if (uiState.selectedTab == Task.State.TODO) uiState.tasks.size else null
                ),
                isSelected = uiState.selectedTab == Task.State.TODO
            ),
            Selectable(
                value = TabItem(
                    "Done",
                    badgeCount = if (uiState.selectedTab == Task.State.DONE) uiState.tasks.size else null
                ),
                isSelected = uiState.selectedTab == Task.State.DONE
            )
        ),
        onTabSelected = {/*TODO*/ },
    )
}