package com.baghdad.tudee.ui.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.chips.ChipTextWithArrowIcon
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.designSystem.theme.TudeeTheme
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.composable.MoodSliderChangeable
import com.baghdad.tudee.ui.composable.OverviewCards
import com.baghdad.tudee.ui.composable.PairOfTask
import com.baghdad.tudee.ui.composable.SnakeBar
import com.baghdad.tudee.ui.composable.TasksEmptyScreen
import com.baghdad.tudee.ui.composable.TopTudeeBar
import com.baghdad.tudee.ui.composable.TudeeBottomSheet
import com.baghdad.tudee.ui.composable.button.FloatingActionButton
import com.baghdad.tudee.ui.composable.taskDetailsBottomSheet.TaskDetailsBottomSheet
import com.baghdad.tudee.ui.screens.homeScreen.addEditTask.AddEditTaskBottomSheet
import com.baghdad.tudee.ui.utils.formatDate
import com.baghdad.tudee.ui.utils.now
import com.baghdad.tudee.viewModel.homescreenViewModel.HomeScreenViewModel
import kotlinx.datetime.LocalDate
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(navigateToTaskScreen: (Task.State) -> Unit, modifier: Modifier = Modifier) {
    HomeScreenContent(navigateToTaskScreen, modifier = modifier)
}

@Composable
fun HomeScreenContent(navigateToTaskScreen: (Task.State) -> Unit, modifier: Modifier = Modifier) {
    val viewModel: HomeScreenViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                painter = painterResource(R.drawable.ic_add),
                onClick = {
                    viewModel.toggleAddNewTaskDialog()
                },
                modifier = Modifier.padding(16.dp)
            )
        }


    ) {
        BottomSheetHandler(state, viewModel)
        it
        Box(modifier = modifier.fillMaxSize()) {
            Column(
                modifier
                    .fillMaxSize()
                    .background(Theme.color.primaryColor.normal)
                    .padding(WindowInsets.statusBars.asPaddingValues())
            ) {
                TopTudeeBar(
                    title = "Tudee",
                    description = stringResource(R.string.Your_personal_task_manager),
                    isDay = state.isDark.not(),
                    onChangeTheme = {
                        viewModel.onClickSwitchTheme()
                    }
                )
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .background(Theme.color.surfaceColor.surface)
                ) {
                    item { StatusTasksSection(state, modifier = Modifier.fillParentMaxWidth()) }
                    if (state.inProgressTasks.isNotEmpty())
                        item {
                            HorizontalTaskSection(
                                name = stringResource(R.string.in_progress),
                                numberOfItem = state.inProgressTasks.size,
                                tasks = state.inProgressTasks,
                                state = state,
                                viewModel = viewModel,
                                modifier = Modifier.fillParentMaxWidth().padding(bottom = 8.dp),
                                onClick = { navigateToTaskScreen(Task.State.IN_PROGRESS) }
                            )
                        }
                    if (state.todoTasks.isNotEmpty())
                        item {
                            HorizontalTaskSection(
                                name = stringResource(R.string.to_do),
                                numberOfItem = state.todoTasks.size,
                                tasks = state.todoTasks,
                                state = state,
                                viewModel = viewModel,
                                modifier = Modifier.fillParentMaxWidth().padding(bottom = 8.dp),
                                onClick = { navigateToTaskScreen(Task.State.TODO) }
                            )
                        }
                    if (state.doneTasks.isNotEmpty())
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            HorizontalTaskSection(
                                name = stringResource(R.string.done),
                                numberOfItem = state.doneTasks.size,
                                tasks = state.doneTasks,
                                state = state,
                                viewModel = viewModel,
                                modifier = Modifier.fillParentMaxWidth().padding(bottom = 8.dp),
                                onClick = { navigateToTaskScreen(Task.State.DONE) }
                            )
                        }
                    if (state.todoTasks.isEmpty() && state.inProgressTasks.isEmpty() && state.doneTasks.isEmpty()) {
                        item { TasksEmptyScreen() }
                    }

                }
            }
            SnakeBar(
                Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.TopCenter)
                    .padding(top = 120.dp),
                message = state.showSnackBar.message,
                isSuccess = !state.showSnackBar.isError,
                isVisible = state.showSnackBar.isVisible
            )

        }
    }
}

@Composable
 fun TextDateIcon(
    text: String,
    modifier: Modifier = Modifier,
    icon: Painter,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = icon,
            contentDescription = "date icon",
            modifier = Modifier.padding(end = 8.dp),
            tint = Theme.color.textColor.body
        )
        Text(
            text = text,
            style = Theme.typography.label.medium,
            color = Theme.color.textColor.body
        )

    }
}




@Composable
private fun BottomSheetHandler(state: HomeScreenUIState, viewModel: HomeScreenViewModel) {
    if (state.showEditTask) {
        TudeeBottomSheet(
            isVisible = state.showEditTask,
            onDismiss = { viewModel.togileEditTaskDialog(initialTaskId = null) }
        ) {
            AddEditTaskBottomSheet(
                initial = state.editTaskState.currentTask,
                state = state.editTaskState.categories,
                addEditTaskInteractionListener = viewModel,
                onDismiss = { viewModel.togileEditTaskDialog(null) }
            )
        }
    } else if (state.showAddNewTask) {
        TudeeBottomSheet(
            isVisible = state.showAddNewTask,
            onDismiss = { viewModel.toggleAddNewTaskDialog() }
        ) {
            AddEditTaskBottomSheet(
                initial = state.addTaskState.currentTask,
                state = state.categories,
                addEditTaskInteractionListener = viewModel,
                onDismiss = {
                    viewModel.toggleAddNewTaskDialog()
                }
            )
        }

    } else if (state.showTaskDetails) {

        TudeeBottomSheet(
            isVisible = state.showTaskDetails,
            onDismiss = { viewModel.toggleTaskDetailsDialog() }
        ) {
            TaskDetailsBottomSheet(
                isVisible = state.showTaskDetails,
                onDismiss = { viewModel.toggleTaskDetailsDialog() },
                task = state.taskDetailsState,
                onEditClick = {
                    viewModel.toggleTaskDetailsDialog()
                    viewModel.togileEditTaskDialog(state.taskDetailsState.id)
                },
                onUpdateTaskState = { newState ->
                    if (newState == Task.State.IN_PROGRESS) {
                        viewModel.moveTaskToInProgress(state.taskDetailsState.id)
                    } else {
                        viewModel.moveTaskToDone(state.taskDetailsState.id)
                    }
                    viewModel.toggleTaskDetailsDialog()
                }
            )
        }
    }
}

@Composable
fun TextHeadTaskSection(
    name: String,
    numberOfItem: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Row(
        modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            color = Theme.color.textColor.title,
            style = Theme.typography.title.large
        )
        ChipTextWithArrowIcon(numberOfItem) {
            onClick()
        }

    }
}
@Composable
private fun StatusTasksSection(state: HomeScreenUIState, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .zIndex(-1f)
            .fillMaxWidth()
            .height(45.dp)
            .background(Theme.color.primaryColor.normal)
    )
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .offset(y = -45.dp)
            .background(
                Theme.color.surfaceColor.surface,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        TextDateIcon(
            text = stringResource(R.string.today, LocalDate.now().formatDate()),
            icon = painterResource(R.drawable.ic_date)
        )

        MoodSliderChangeable(state)

        OverViewSection(state)
    }
}
@Composable
private fun OverViewSection(state: HomeScreenUIState){
    Text(
        text = stringResource(R.string.overview),
        style = Theme.typography.title.large,
        color = Theme.color.textColor.title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, bottom = 8.dp)
    )
    OverviewCards(
        state = state,
        modifier = Modifier
    )

}

@Composable
fun HorizontalTaskSection(
    name: String,
    numberOfItem: Int,
    tasks: List<Task>,
    state: HomeScreenUIState,
    viewModel: HomeScreenViewModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(modifier = modifier) {
        TextHeadTaskSection(
            name = name,
            numberOfItem = numberOfItem,
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 8.dp
            ),
            onClick = onClick
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (tasks.isNotEmpty()) {
                items(tasks.chunked(2)) { pair ->
                    PairOfTask(
                        modifier = Modifier.fillParentMaxWidth(0.95f),
                        pair = pair,
                        state = state,
                        viewModel = viewModel,
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    TudeeTheme {
        HomeScreenContent(navigateToTaskScreen = {})
    }
}


enum class TaskState {
    TODO,
    IN_PROGRESS,
    DONE,
}