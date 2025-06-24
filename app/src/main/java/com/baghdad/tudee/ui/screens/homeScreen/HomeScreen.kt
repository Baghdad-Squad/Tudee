package com.baghdad.tudee.ui.screens.homeScreen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.designSystem.theme.TudeeTheme
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.composable.CategoryTaskCard
import com.baghdad.tudee.ui.composable.OverviewCard
import com.baghdad.tudee.ui.composable.SnakeBar
import com.baghdad.tudee.ui.composable.TextHeadTaskSection
import com.baghdad.tudee.ui.composable.TopTudeeBar
import com.baghdad.tudee.ui.composable.TudeeBottomSheet
import com.baghdad.tudee.ui.composable.taskDetailsBottomSheet.TaskDetailsBottomSheet
import com.baghdad.tudee.ui.composable.button.FloatingActionButton
import com.baghdad.tudee.ui.composable.texts.TextDateIcon
import com.baghdad.tudee.ui.composable.texts.TextMoodIcon
import com.baghdad.tudee.ui.screens.homeScreen.addEditTask.AddEditTaskBottomSheet
import com.baghdad.tudee.ui.utils.formatDate
import com.baghdad.tudee.ui.utils.insideBorder
import com.baghdad.tudee.ui.utils.noRippleClickable
import com.baghdad.tudee.ui.utils.now
import com.baghdad.tudee.viewModel.homescreenViewModel.HomeScreenViewModel
import kotlinx.datetime.LocalDate
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    HomeScreenContent(modifier = modifier)
}

@Composable
fun HomeScreenContent(modifier: Modifier = Modifier) {
    val viewModel: HomeScreenViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                painter = painterResource(R.drawable.ic_add),
                onClick = {
                    viewModel.togileEditTaskDialog()
                },
                modifier = Modifier.padding(16.dp)
            )
            if (state.showEditTask) {
                TudeeBottomSheet(
                    isVisible = state.showEditTask,
                    onDismiss = { viewModel.togileEditTaskDialog() }
                ) {
                    AddEditTaskBottomSheet(
                        state = state.editTaskState.categories,
                        addEditTaskInteractionListener = viewModel,
                        onDismiss = { viewModel.togileEditTaskDialog() }
                    )
                }
            } else if (state.showAddNewTask) {
                TudeeBottomSheet(
                    isVisible = state.showAddNewTask,
                    onDismiss = {
                        viewModel
                            .toggleAddNewTaskDialog()
                    }
                ) {
                    AddEditTaskBottomSheet(
                        state = state.editTaskState.categories,
                        addEditTaskInteractionListener = viewModel,
                        onDismiss = { viewModel.toggleAddNewTaskDialog() }
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
                            //TODO: show edit task bottom sheet
                            viewModel.toggleTaskDetailsDialog()
                        },
                        onUpdateTaskState = { newState ->
                            if(newState == Task.State.IN_PROGRESS){
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

    ) {
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
                    description = "Your personal task manager",
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
                    item {

                        Box(
                            modifier = Modifier
                                .zIndex(-1f)
                                .fillMaxWidth()
                                .height(45.dp)
                                .background(Theme.color.primaryColor.normal)
                        )
                        Column(
                            modifier = Modifier
                                .fillParentMaxWidth()
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
                            SliderSection(state = state)
                            OverViewSection(state)

                        }
                    }
                    if (state.inProgressTasks.isNotEmpty())
                        item {
                            TextHeadTaskSection(
                                name = stringResource(R.string.in_progress),
                                numberOfItem = state.inProgressTasks.size,
                                modifier = Modifier.padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 8.dp
                                ),
                            ) {
                                navigateToTasks(
                                    state.inProgressTasks.firstOrNull()?.categoryId ?: 0L
                                )
                            }

                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                if (state.inProgressTasks.isNotEmpty())
                                    items(state.inProgressTasks.chunked(2)) { pair ->
                                        Column(modifier = Modifier.fillMaxWidth()) {
                                            // First item in the pair
                                            CategoryTaskCard(
                                                title = pair[0].title,
                                                description = pair[0].description,
                                                priorityTask = pair[0].priority,
                                                icon = painterResource(R.drawable.ic_quran),
                                                modifier = Modifier
                                                    .fillParentMaxWidth(0.95f)
                                                    .padding(bottom = 8.dp)
                                            ) {
                                                viewModel.getTaskDetailsById(
                                                    id = pair[0].id
                                                )
                                            }

                                            if (pair.size > 1) {
                                                CategoryTaskCard(
                                                    title = pair[1].title,
                                                    description = pair[1].description,
                                                    priorityTask = pair[1].priority,
                                                    icon = painterResource(R.drawable.ic_quran),
                                                    modifier = Modifier.fillParentMaxWidth(0.95f)
                                                ) {
                                                    viewModel.getTaskDetailsById(
                                                        id = pair[1].id
                                                    )
                                                }
                                            }
                                        }
                                    }

                            }
                        }
                    if (state.todoTasks.isNotEmpty())
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            TextHeadTaskSection(
                                name = stringResource(R.string.to_do),
                                numberOfItem = state.doneTasks.size,
                                modifier = Modifier
                                    .fillParentMaxWidth(0.95f)
                                    .padding(bottom = 8.dp)
                                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                            ) {
                                navigateToTasks(state.todoTasks.firstOrNull()?.categoryId ?: 0L)
                            }

                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                val taskPairs =
                                    state.todoTasks.chunked(2) // Split into [[task1, task2], [task3, task4], ...]

                                itemsIndexed(taskPairs) { index, pair ->
                                    Column(modifier = Modifier.fillMaxWidth()) {
                                        CategoryTaskCard(
                                            title = pair[0].title,
                                            description = pair[0].description,
                                            priorityTask = pair[0].priority,
                                            icon = painterResource(R.drawable.ic_quran),
                                            modifier = Modifier
                                                .fillParentMaxWidth(0.95f)
                                                .padding(bottom = 8.dp)
                                        ) {
                                            viewModel.getTaskDetailsById(id = pair[0].id)
                                        }

                                        if (pair.size > 1) {
                                            CategoryTaskCard(
                                                title = pair[1].title,
                                                description = pair[1].description,
                                                priorityTask = pair[1].priority,
                                                icon = painterResource(R.drawable.ic_quran),
                                                modifier = Modifier
                                                    .fillParentMaxWidth(0.95f)
                                                    .padding(bottom = 8.dp)
                                            ) {
                                                viewModel.getTaskDetailsById(id = pair[1].id)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    if (state.doneTasks.isNotEmpty())
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            TextHeadTaskSection(
                                name = stringResource(R.string.done),
                                numberOfItem = state.doneTasks.size,
                                modifier = Modifier.padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 8.dp
                                ),
                            ) {
                                navigateToTasks(state.doneTasks.firstOrNull()?.categoryId ?: 0L)
                            }

                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 32.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(state.doneTasks.chunked(2)) { pair ->
                                    Column(modifier = Modifier.fillMaxWidth()) {

                                        CategoryTaskCard(
                                            title = pair[0].title,
                                            description = pair[0].description,
                                            priorityTask = pair[0].priority,
                                            icon = painterResource(R.drawable.ic_quran),
                                            modifier = Modifier
                                                .fillParentMaxWidth(0.95f)
                                                .padding(bottom = 8.dp)
                                        ) {
                                            viewModel.getTaskDetailsById(id = pair[0].id)
                                        }

                                        if (pair.size > 1) {
                                            CategoryTaskCard(
                                                title = pair[1].title,
                                                description = pair[1].description,
                                                priorityTask = pair[1].priority,
                                                icon = painterResource(R.drawable.ic_quran),
                                                modifier = Modifier.fillParentMaxWidth(0.95f)
                                            ) {
                                                viewModel.getTaskDetailsById(id = pair[0].id)
                                            }
                                        }
                                    }
                                }
                            }
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
private fun EditBottomSheet(state: HomeScreenUIState, viewModel: HomeScreenViewModel) {
    if (state.showEditTask) {
        TudeeBottomSheet(
            isVisible = state.showEditTask,
            onDismiss = { viewModel.togileEditTaskDialog() }
        ) {
            AddEditTaskBottomSheet(
                state = state.editTaskState.categories,
                addEditTaskInteractionListener = viewModel,
                onDismiss = { viewModel.togileEditTaskDialog() }
            )
        }
    }
}

@Composable
private fun AddBottomSheet(state: HomeScreenUIState, viewModel: HomeScreenViewModel) {
    if (state.showAddNewTask) {
        TudeeBottomSheet(
            isVisible = state.showAddNewTask,
            onDismiss = {
                viewModel
                    .toggleAddNewTaskDialog()
            }
        ) {
            AddEditTaskBottomSheet(
                state = state.editTaskState.categories,
                addEditTaskInteractionListener = viewModel,
                onDismiss = { viewModel.toggleAddNewTaskDialog() }
            )
        }
    }
}

@Composable
private fun TaskDetailsBottomSheet(state: HomeScreenUIState, viewModel: HomeScreenViewModel) {
    if (state.showTaskDetails) {

        TudeeBottomSheet(
            isVisible = state.showTaskDetails,
            onDismiss = { viewModel.toggleTaskDetailsDialog() }
        ) {
            TaskDetailsBottomSheet(
                isVisible = state.showTaskDetails,
                onDismiss = { viewModel.toggleTaskDetailsDialog() },
                task = state.taskDetailsState,
                onEditClick = {
                    viewModel.togileEditTaskDialog()
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
private fun TextMoodSection(state: HomeScreenUIState) {
    Column(modifier = Modifier.fillMaxWidth(0.6f)) {

        TextMoodIcon(
            text = when (state.sliderState) {
                SliderState.STAY_WORKING -> stringResource(R.string.stay_working)
                SliderState.TADOO -> stringResource(R.string.tadaa)
                SliderState.ZERO_PROGRESS -> stringResource(R.string.zero_progress)
                SliderState.NOTHING_IN_YOUR_LIST -> stringResource(R.string.nothing_on_your_list)

            },
            icon = painterResource(
                id = when (state.sliderState) {
                    SliderState.STAY_WORKING -> R.drawable.ic_okay_feedback
                    SliderState.TADOO -> R.drawable.ic_good_feedback
                    SliderState.ZERO_PROGRESS -> R.drawable.ic_bad_feedback
                    SliderState.NOTHING_IN_YOUR_LIST -> R.drawable.ic_poor_feedback

                }
            )
        )
        Text(
            text = when (state.sliderState) {
                SliderState.STAY_WORKING -> stringResource(R.string.you_ve_completed_3_out_of_10_tasks_keep_going)
                SliderState.TADOO -> stringResource(R.string.you_re_doing_amazing_tudee_is_proud_of_you)
                SliderState.ZERO_PROGRESS -> stringResource(R.string.you_just_scrolling_not_working_tudee_is_watching_back_to_work)
                SliderState.NOTHING_IN_YOUR_LIST -> stringResource(R.string.fill_your_day_with_something_awesome)

            },
            style = Theme.typography.body.small,
            color = Theme.color.textColor.body,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}


@Composable
fun SliderSection(state: HomeScreenUIState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        TextMoodSection(state)
        Box(contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        Theme.color.primaryColor.normal.copy(0.4f),
                        shape = RoundedCornerShape(100)
                    )
            )

            Image(
                painter = painterResource(
                    when (state.sliderState) {
                        SliderState.STAY_WORKING -> R.drawable.happy_robot
                        SliderState.TADOO -> R.drawable.image_cute_robot
                        SliderState.ZERO_PROGRESS -> R.drawable.image_angry
                        SliderState.NOTHING_IN_YOUR_LIST -> R.drawable.happy_robot

                    }
                ),
                contentDescription = when (state.sliderState) {
                    SliderState.STAY_WORKING -> "Happy Robot"
                    SliderState.TADOO -> "Cute Robot"
                    SliderState.ZERO_PROGRESS -> "Angry Robot"
                    SliderState.NOTHING_IN_YOUR_LIST -> "Happy Robot"
                },
            )
        }
    }
}

@Composable
fun OverViewSection(state: HomeScreenUIState) {
    Text(
        text = stringResource(R.string.overview),
        style = Theme.typography.title.large,
        color = Theme.color.textColor.title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, bottom = 8.dp)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        OverviewCard(
            count = state.doneTasks.size,
            background = Theme.color.status.greenAccent,
            taskState = TaskState.DONE,
            modifier = Modifier.weight(1f)
        )
        OverviewCard(
            count = state.inProgressTasks.size,
            background = Theme.color.status.yellowAccent,
            taskState = TaskState.IN_PROGRESS,
            modifier = Modifier.weight(1f)
        )
        OverviewCard(
            count = state.todoTasks.size,
            background = Theme.color.status.purpleAccent,
            taskState = TaskState.TODO,
            modifier = Modifier.weight(1f)
        )
    }
}


enum class TaskState {
    TODO,
    IN_PROGRESS,
    DONE,
}