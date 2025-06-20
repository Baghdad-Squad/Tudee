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
import com.baghdad.tudee.ui.composable.SnakeBar
import com.baghdad.tudee.ui.composable.SnakeBar
import com.baghdad.tudee.ui.composable.TopTudeeBar
import com.baghdad.tudee.ui.composable.TudeeBottomSheet
import com.baghdad.tudee.ui.composable.taskDetailsBottomSheet.TaskDetails
import com.baghdad.tudee.ui.composable.TudeeBottomSheet
import com.baghdad.tudee.ui.composable.button.FloatingActionButton
import com.baghdad.tudee.ui.composable.taskDetailsBottomSheet.TaskDetails
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
                painter = painterResource(R.drawable.ic_black_note),
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
                    TaskDetails(
                        isVisible = state.showTaskDetails,
                        onDismiss = { viewModel.toggleTaskDetailsDialog() },
                        icon = painterResource(id = R.drawable.ic_quran),
                        taskPriority = Task.Priority.LOW,
                        taskState = Task.State.IN_PROGRESS,
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
                    isDay = state.isDark,
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
                                Color.White,
                                shape = RoundedCornerShape(16.dp)
                            )
                    ) {
                        TextDateIcon(
                            text = stringResource(R.string.today, LocalDate.now().formatDate()),
                            icon = painterResource(R.drawable.ic_date)
                        )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 12.dp, end = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {

                                Column(modifier = Modifier.fillMaxWidth(0.6f)) {

                                    TextMoodIcon(
                                        text = when (state.sliderState) {
                                            SliderState.STAY_WORKING -> "Stay working!"
                                            SliderState.TADOO -> "Tadaa"
                                            SliderState.ZERO_PROGRESS -> "Zero progress?!"
                                            SliderState.NOTHING_IN_YOUR_LIST -> "Nothing on your list…"

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
                }
                if (state.inProgressTasks.isNotEmpty())
                    item {
                        TextHeadTaskSection(
                            name = stringResource(R.string.in_progress),
                            numberOfItem = 12,
                            modifier = Modifier.padding(
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 8.dp
                            ),
                        ) {

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
                            numberOfItem = 12,
                            modifier = Modifier
                                .fillParentMaxWidth(0.95f)
                                .padding(bottom = 8.dp)
                                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                        ) {

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
                            numberOfItem = 12,
                            modifier = Modifier.padding(
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 8.dp
                            ),
                        ) {

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

                                    }

                                    if (pair.size > 1) {
                                        CategoryTaskCard(
                                            title = pair[1].title,
                                            description = pair[1].description,
                                            priorityTask = pair[1].priority,
                                            icon = painterResource(R.drawable.ic_quran),
                                            modifier = Modifier.fillParentMaxWidth(0.95f)
                                        ) {}
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
private fun TextDateIcon(
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
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = text,
            style = Theme.typography.label.medium,
            color = Theme.color.textColor.body
        )

    }
}


@Composable
private fun TextMoodIcon(
    text: String,
    modifier: Modifier = Modifier,
    icon: Painter = painterResource(R.drawable.ic_okay_feedback),
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = text,
            style = Theme.typography.title.small,
            color = Theme.color.textColor.title
        )
        Image(
            painter = icon,
            contentDescription = "mood icon",
            modifier = Modifier.padding(end = 8.dp)
        )
    }
}

@Composable
fun OverviewCard(
    count: Int,
    background: Color,
    taskState: TaskState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .zIndex(999f)
            .height(112.dp)
            .width(96.dp)
            .background(background, shape = RoundedCornerShape(20.dp))
    ) {
        Column(modifier.padding(12.dp)) {
            Box(
                modifier
                    .size(40.dp)
                    .background(
                        color = Color(0x3DFFFFFF),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .insideBorder(1.dp, Color(0x1FFFFFFF), 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(
                        when (taskState) {
                            TaskState.TODO -> R.drawable.ic_overview_card_todo
                            TaskState.IN_PROGRESS -> R.drawable.ic_overview_card_in_progress
                            TaskState.DONE -> R.drawable.ic_overview_card_done
                        }
                    ), contentDescription = when (taskState) {
                        TaskState.TODO -> "To Do Icon"
                        TaskState.IN_PROGRESS -> "In Progress Icon"
                        TaskState.DONE -> "Done Icon"
                    },
                    tint = Theme.color.textColor.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }
            Text(
                text = count.toString(),
                style = Theme.typography.headline.medium,
                color = Theme.color.textColor.onPrimary,
            )
            Text(
                text = when (taskState) {
                    TaskState.TODO -> "To Do"
                    TaskState.IN_PROGRESS -> "In Progress"
                    TaskState.DONE -> "Done"
                },
                style = Theme.typography.label.small,
                color = Theme.color.textColor.onPrimaryCaption,
            )
        }

        Icon(
            painter = painterResource(R.drawable.overview_card_background),
            contentDescription = "Overview Card Background",
            modifier = Modifier
                .zIndex(-1f)
                .align(Alignment.TopEnd)
                .background(
                    Color.Transparent, shape = RoundedCornerShape(
                        topEnd = 24.dp, topStart = 24.dp
                    )
                ),
            tint = Color.Unspecified
        )

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
private fun ChipTextWithArrowIcon(
    numberOfItem: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {

    Row(
        modifier
            .background(
                Theme.color.surfaceColor.surfaceHigh,
                shape = RoundedCornerShape(100.dp)
            )
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .noRippleClickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "$numberOfItem",
            style = Theme.typography.label.medium,
            color = Theme.color.textColor.body
        )
        Icon(
            painter = painterResource(R.drawable.ic_arrow),
            contentDescription = "Arrow Icon",
            tint = Theme.color.textColor.body
        )

    }

}

@Preview
@Composable
fun HomeScreenPreview() {
    TudeeTheme {
        HomeScreenContent()
    }
}


enum class TaskState {
    TODO,
    IN_PROGRESS,
    DONE,
}