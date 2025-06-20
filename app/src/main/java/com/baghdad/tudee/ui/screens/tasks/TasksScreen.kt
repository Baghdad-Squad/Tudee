package com.baghdad.tudee.ui.screens.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.ui.composable.button.FloatingActionButton
import com.baghdad.tudee.ui.screens.tasks.components.HorizontalDayChipsSetup
import com.baghdad.tudee.ui.screens.tasks.components.StatusTabs
import com.baghdad.tudee.ui.screens.tasks.components.TasksList
import com.baghdad.tudee.ui.screens.tasks.components.TasksEmptyScreen
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import com.baghdad.tudee.domain.entity.Task

@Composable
fun TasksScreen(
    viewModel: TasksViewModel = koinViewModel(),
    initialState: Task.State
) {

    LaunchedEffect(true) {
        viewModel.onTabSelected(initialState)
    }
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    TasksScreenContent(
        uiState = state,
        tasksInteractionListener = viewModel,
    )

}

@Composable
fun TasksScreenContent(
    uiState: TasksUiState,
    tasksInteractionListener: TasksInteractionListener,
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surfaceColor.surface)
                .padding(vertical = 20.dp)
        ) {
            Text(
                text = stringResource(R.string.tasks),
                style = Theme.typography.title.large,
                color = Theme.color.textColor.title,
                modifier = Modifier.padding(bottom = 20.dp, start = 16.dp, top = 20.dp),
            )
            HorizontalDayChipsSetup(
                tasksInteractionListener = tasksInteractionListener,
                uiState = uiState,
                modifier = Modifier.padding(bottom = 8.dp),
            )


            StatusTabs(
                uiState = uiState,
                tasksInteractionListener = tasksInteractionListener
            )

            if (uiState.tasksDisplayed.isNotEmpty()) {
                TasksList(
                    uiState = uiState,
                )
            } else {
                TasksEmptyScreen()
            }

        }
        FloatingActionButton(
            onClick = { /*TODO*/ },
            painter = painterResource(id = R.drawable.ic_add),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(y = -(72).dp, x = -(12).dp)
        )
    }

}

