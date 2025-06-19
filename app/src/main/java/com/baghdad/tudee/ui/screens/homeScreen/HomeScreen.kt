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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.designSystem.theme.TudeeTheme
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.composable.CategoryTaskCard
import com.baghdad.tudee.ui.composable.TopTudeeBar
import com.baghdad.tudee.ui.utils.insideBorder
import com.baghdad.tudee.ui.utils.noRippleClickable


@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    HomeScreenContent(modifier = modifier)
}

@Composable
fun HomeScreenContent(modifier: Modifier = Modifier) {

    Column(modifier.fillMaxSize().background(Theme.color.primaryColor.normal).padding( WindowInsets.statusBars.asPaddingValues())) {
        TopTudeeBar(
            title = "Tudee",
            description = "Your personal task manager",
            onChangeTheme = { /* TODO */ }
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
                        text = "today, 22 Jun 2025",
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
                                text = "Stay working!",
                                icon = painterResource(R.drawable.ic_okay_feedback)
                            )
                            Text(
                                text = "You've completed 3 out of 10 tasks Keep going!",
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
                                    R.drawable.happy_robot
                                ),
                                contentDescription = "happy robot",
                            )
                        }

                    }

                    Text(
                        text = "Overview",
                        style = Theme.typography.title.large,
                        color = Theme.color.textColor.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, bottom = 8.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, end = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OverviewCard(
                            count = 2,
                            background = Theme.color.status.greenAccent,
                            taskState = TaskState.DONE,
                            modifier = Modifier.weight(1f)
                        )
                        OverviewCard(
                            count = 16,
                            background = Theme.color.status.yellowAccent,
                            taskState = TaskState.IN_PROGRESS,
                            modifier = Modifier.weight(1f)
                        )
                        OverviewCard(
                            count = 1,
                            background = Theme.color.status.purpleAccent,
                            taskState = TaskState.TODO,
                            modifier = Modifier.weight(1f)
                        )
                    }

                }
            }

            item {
                TextHeadTaskSection(
                    name = "In progress ",
                    numberOfItem = 12,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                ) {

                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(5) {
                        Column(modifier = Modifier.fillMaxWidth()) {

                            CategoryTaskCard(
                                title = "Organize Study Desk",
                                description = "Review cell structure and functions for tomorrow...",
                                priorityTask = Task.Priority.MEDIUM,
                                icon = painterResource(R.drawable.ic_quran),
                                modifier = Modifier
                                    .fillParentMaxWidth(0.95f)
                                    .padding(bottom = 8.dp)
                            ) {}
                            CategoryTaskCard(
                                title = "Organize Study Desk",
                                description = "Review cell structure and functions for tomorrow...",
                                priorityTask = Task.Priority.MEDIUM,
                                icon = painterResource(R.drawable.ic_quran),
                                modifier = Modifier.fillParentMaxWidth(0.95f)
                            ) {}
                        }
                    }

                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                TextHeadTaskSection(
                    name = "To Do ",
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
                    items(5) {
                        Column() {

                            CategoryTaskCard(
                                title = "Organize Study Desk",
                                description = "Review cell structure and functions for tomorrow...",
                                priorityTask = Task.Priority.MEDIUM,
                                icon = painterResource(R.drawable.ic_quran),
                                modifier = Modifier
                                    .fillParentMaxWidth(0.95f)
                                    .padding(bottom = 8.dp)
                                    .padding(bottom = 8.dp)
                            ) {}
                            CategoryTaskCard(
                                title = "Organize Study Desk",
                                description = "Review cell structure and functions for tomorrow...",
                                priorityTask = Task.Priority.MEDIUM,
                                icon = painterResource(R.drawable.ic_quran),
                                modifier = Modifier
                                    .fillParentMaxWidth(0.95f)
                                    .padding(bottom = 8.dp)

                            ) {}
                        }
                    }

                }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                TextHeadTaskSection(
                    name = "Done ",
                    numberOfItem = 12,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                ) {

                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(5) {
                        Column() {

                            CategoryTaskCard(
                                title = "Organize Study Desk",
                                description = "Review cell structure and functions for tomorrow...",
                                priorityTask = Task.Priority.MEDIUM,
                                icon = painterResource(R.drawable.ic_quran),
                                modifier = Modifier
                                    .fillParentMaxWidth(0.95f)
                                    .padding(bottom = 8.dp)
                                    .padding(bottom = 8.dp)
                            ) {}
                            CategoryTaskCard(
                                title = "Organize Study Desk",
                                description = "Review cell structure and functions for tomorrow...",
                                priorityTask = Task.Priority.MEDIUM,
                                icon = painterResource(R.drawable.ic_quran),
                                modifier = Modifier
                                    .fillParentMaxWidth(0.95f)
                                    .padding(bottom = 8.dp)

                            ) {}
                        }
                    }

                }
            }

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