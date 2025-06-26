package com.baghdad.tudee.ui.screens.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.composable.AnimatedSnackbar
import com.baghdad.tudee.ui.composable.SnackbarState
import com.baghdad.tudee.ui.composable.TudeeBottomSheet
import com.baghdad.tudee.ui.composable.TudeeScaffold
import com.baghdad.tudee.ui.composable.button.FloatingActionButton
import com.baghdad.tudee.ui.composable.delete_item.ShowDeleteTaskSheet
import com.baghdad.tudee.ui.composable.taskDetailsBottomSheet.TaskDetailsBottomSheet
import com.baghdad.tudee.ui.screens.tasks.components.HorizontalDayChipsSetup
import com.baghdad.tudee.ui.screens.tasks.components.StatusTabs
import com.baghdad.tudee.ui.screens.tasks.components.TasksHorizontalPager
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@Composable
fun TasksScreen(
    viewModel: TasksViewModel = koinViewModel(),
    initialState: Task.State
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarState by viewModel.snackbarState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.onTabSelected(initialState)
    }

    TasksScreenContent(
        state = state,
        tasksInteractionListener = viewModel,
        snackbarState = snackbarState
    )
}


@Composable
fun TasksScreenContent(
    state: TasksScreenState,
    tasksInteractionListener: TasksInteractionListener,
    snackbarState: SnackbarState,
    ) {
    val pagerState = rememberPagerState { 3 }
    val scope = rememberCoroutineScope()
    LaunchedEffect(pagerState.currentPage) {
        tasksInteractionListener.onTabSelected(Task.State.entries[pagerState.currentPage])
    }

    TudeeScaffold(
        modifier = Modifier
            .background(Theme.color.surfaceColor.surface),
        topBar = {
            Column (
                modifier = Modifier
                    .background(Theme.color.surfaceColor.surfaceHigh)
                    .fillMaxWidth()
                    .padding(WindowInsets.statusBars.asPaddingValues())
            ){
                Text(
                    text = stringResource(R.string.tasks),
                    style = Theme.typography.title.large,
                    color = Theme.color.textColor.title,
                    modifier = Modifier.padding(bottom = 20.dp, start = 16.dp),
                )
                HorizontalDayChipsSetup(
                    tasksInteractionListener = tasksInteractionListener,
                    uiState = state,
                    modifier = Modifier.padding(bottom = 8.dp),
                )

                StatusTabs(
                    uiState = state,
                    onTabSelected = {
                        scope.launch {
                            pagerState.animateScrollToPage(it.ordinal)
                        }
                    },
                    selectedTab = state.selectedTab
                )
            }

        },
        floatingActionButton = {
            FloatingActionButton(
                painter = painterResource(id = R.drawable.ic_add_category),
                onClick = {
                    tasksInteractionListener.toggleAddEditTaskDialog()
                }
            )
        },
        snackbar = {
            AnimatedSnackbar(
                snackbarState = snackbarState
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surfaceColor.surface)
        ) {
            TasksHorizontalPager(
                uiState = state,
                onTaskClick = {
                    tasksInteractionListener.toggleTaskDetailsDialog(it)
                },
                onDeleteTask = tasksInteractionListener::onDeleteTask,
                pagerState = pagerState,
            )



            if (state.showDeleteSheet && state.taskToDelete != null) {
                ShowDeleteTaskSheet(
                    onDeleteConfirmed = tasksInteractionListener:: onConfirmDelete ,
                    onCancelConfirmed = tasksInteractionListener::onCancelDelete,
                    isLoading = false,
                    isVisible = true
                )
            }
            if (state.showAddNewTask) {
                TudeeBottomSheet(
                    isVisible = state.showAddNewTask,
                    onDismiss = { tasksInteractionListener.toggleAddEditTaskDialog() }
                ) {
//                    AddEditTaskBottomSheet(
//                        initial = state.initialTask,
//                        state = state.categories,
//                        addEditTaskInteractionListener = tasksInteractionListener::onClickSaveTask,
//                        onDismiss = {
//                            tasksInteractionListener.toggleAddEditTaskDialog()
//                        }
//                    )
                }
            }
            if (state.showTaskDetailsBottomSheet) {
                TaskDetailsBottomSheet(
                    isVisible = state.showTaskDetailsBottomSheet,
                    onDismiss = tasksInteractionListener::toggleTaskDetailsDialog,
                    task = state.selectedTaskDetails,
                    onEditClick = {
                        tasksInteractionListener.toggleAddEditTaskDialog(state.selectedTaskDetails.id)
                        tasksInteractionListener.toggleTaskDetailsDialog()
                    },
                    onUpdateTaskState = { newState ->
                        tasksInteractionListener.updateTaskState(
                            state.selectedTaskDetails.id,
                            newState
                        )
                    }
                )
            }
        }
    }
}


