package com.baghdad.tudee.ui.screens.categoryTasksScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.composable.CategoryTaskCard
import com.baghdad.tudee.ui.composable.TabItem
import com.baghdad.tudee.ui.composable.Tabs
import com.baghdad.tudee.ui.shared.Selectable
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryTasksScreen(viewModel: CategoryTasksViewModel = koinViewModel(),
                        category: Category) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CategoryTasksScreenContent(
        state = state,
        onTabSelected = viewModel::onTabSelected,
        filteredTasks = viewModel::,
        category = category
    )
}

@Composable
private fun CategoryTasksScreenContent(
    state: CategoryTasksScreenUiState,
    onTabSelected: (Task.State) -> Unit,
    filteredTasks: List<Task>,
    category: Category
) {

    Column(modifier = Modifier.padding(top = 28.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .border(
                        width = 1.dp,
                        shape = CircleShape,
                        color = Theme.color.textColor.stroke
                    )
                    .clickable { /*onBackArrowClicked()*/ }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_left_01),
                    contentDescription = stringResource(R.string.arrow_left),
                    tint = Theme.color.textColor.body,
                    modifier = Modifier
                        .padding(10.dp)
                )
            }
            Text(
                text = category.title,
                style = Theme.typography.title.large,
                color = Theme.color.textColor.title
            )
        }

        val tabs = listOf(
            Selectable(
                TabItem("In Progress", state.inProgressTasks.size, Task.State.IN_PROGRESS),
                isSelected = state.selectedTab == Task.State.IN_PROGRESS
            ),
            Selectable(
                TabItem("To Do", state.todoTasks.size, Task.State.TODO),
                isSelected = state.selectedTab == Task.State.TODO
            ),
            Selectable(
                TabItem("Done", state.doneTasks.size, Task.State.DONE),
                isSelected = state.selectedTab == Task.State.DONE
            )
        )
        Tabs(
            selectableTabs = tabs,
            onTabSelected = { tab -> onTabSelected(tab.status) },
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            items(filteredTasks) { task ->
                val icon = painterResource()
                CategoryTaskCard(
                    title = task.title,
                    description = task.description,
                    priorityTask = task.priority,
                    icon = category.image,
                    onClick = { println("Clicked task: ${task.title}") },
                    date = task.date.toString(),
                    showDate = true
                )
            }
        }
    }
}

val sampleTabs = listOf(
    Selectable(
        TabItem(title = "In progress", badgeCount = 4, status = Task.State.IN_PROGRESS),
        isSelected = true
    ),
    Selectable(
        TabItem(title = "To Do", badgeCount = 4, status = Task.State.TODO),
        isSelected = false
    ),
    Selectable(
        TabItem(title = "Done", badgeCount = 4, status = Task.State.DONE),
        isSelected = false
    )
)

@Preview(showBackground = true)
@Composable
fun CategoryTasksScreenPreview() {
//    CategoryTasksScreen(
//        CategoryName = "Coding",
//        onBackArrowClicked = {},
//        taskDate = "12-03-2025",
//    )
}