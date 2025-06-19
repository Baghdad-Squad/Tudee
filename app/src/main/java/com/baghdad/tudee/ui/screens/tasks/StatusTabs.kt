package com.baghdad.tudee.ui.screens.tasks

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.baghdad.tudee.R
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.composable.TabItem
import com.baghdad.tudee.ui.composable.Tabs
import com.baghdad.tudee.ui.shared.Selectable

@Composable
fun StatusTabs(
    tasksInteractionHandler: TasksInteractionHandler,
    uiState: TasksUiState,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Row(
        modifier = modifier
    ) {

        Tabs(
            selectableTabs = listOf(
                Selectable(
                    value = TabItem(
                        stringResource(R.string.in_progress),
                        badgeCount = if (uiState.selectedTab == Task.State.IN_PROGRESS) uiState.tasksDisplayed.size else null
                    ),
                    isSelected = uiState.selectedTab == Task.State.IN_PROGRESS
                ),
                Selectable(
                    value = TabItem(
                        stringResource(R.string.to_do),
                        badgeCount = if (uiState.selectedTab == Task.State.TODO) uiState.tasksDisplayed.size else null
                    ),
                    isSelected = uiState.selectedTab == Task.State.TODO
                ),

                Selectable(
                    value = TabItem(
                        stringResource(R.string.done),
                        badgeCount = if (uiState.selectedTab == Task.State.DONE) uiState.tasksDisplayed.size else null
                    ),
                    isSelected = uiState.selectedTab == Task.State.DONE
                )
            ),
            onTabSelected = {
                tasksInteractionHandler.onTabSelected(it.title.toTaskStatus(context = context))
            },
        )
    }
}

fun String.toTaskStatus(context: Context): Task.State {
    return when (this) {
        context.getString(R.string.to_do) -> Task.State.TODO
        context.getString(R.string.in_progress) -> Task.State.IN_PROGRESS
        context.getString(R.string.done) -> Task.State.DONE
        else -> Task.State.TODO
    }
}