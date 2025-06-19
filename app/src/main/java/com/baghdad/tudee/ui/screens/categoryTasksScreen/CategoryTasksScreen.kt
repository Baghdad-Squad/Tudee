package com.baghdad.tudee.ui.screens.categoryTasksScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.Color
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
import com.baghdad.tudee.ui.utils.getCategoryIconPainter
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryTasksScreen(
    viewModel: CategoryTasksViewModel = koinViewModel(),
    category: Category
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val categoryId = category.id
    viewModel.getTasksByCategoryId(categoryId)
    CategoryTasksScreenContent(
        state = state,
        onTabSelected = viewModel::onTabSelected,
        filteredTasks = state.doneTasks,
        isPredefinedCategory = state.isPredefinedCategory,
        onPencilEditClicked = {},
        onArrowBackClicked ={}
    )
}

@Composable
private fun CategoryTasksScreenContent(
    state: CategoryTasksScreenUiState,
    onTabSelected: (Task.State) -> Unit,
    filteredTasks: List<Task>,
    isPredefinedCategory: Boolean,
    onArrowBackClicked:()->Unit,
    onPencilEditClicked:()->Unit
) {

    Column(modifier = Modifier.padding(top = 28.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconInBox(icon = R.drawable.arrow_left_01, onIconClick = {onArrowBackClicked()})
            Text(
                text = state.categoryName,
                style = Theme.typography.title.large,
                color = Theme.color.textColor.title
            )
            if (isPredefinedCategory) {
                IconInBox(icon = R.drawable.pencil_edit_01, onIconClick = {onPencilEditClicked()})
            }
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
                CategoryTaskCard(
                    title = task.title,
                    description = task.description,
                    priorityTask = task.priority,
                    icon = getCategoryIconPainter(categoryImage = state.categoryImage),
                    onClick = { println("Clicked task: ${task.title}") },
                    date = task.date.toString(),
                    showDate = true
                )
            }
        }
    }
}

@Composable
fun IconInBox(modifier: Modifier = Modifier,
              icon: Int, onIconClick: () -> Unit,
              tint :Color = Theme.color.textColor.body
              ) {
    Box(
        modifier = modifier
            .size(40.dp)
            .border(
                width = 1.dp,
                shape = CircleShape,
                color = Theme.color.textColor.stroke
            )
            .clickable { onIconClick }
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = stringResource(R.string.arrow_left),
            tint = tint,
            modifier = Modifier
                .padding(10.dp)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun CategoryTasksScreenPreview() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IconInBox(icon = R.drawable.arrow_left_01, onIconClick = {})
        Text(
            text = "state.categoryName",
            style = Theme.typography.title.large,
            color = Theme.color.textColor.title
        )
        Spacer(modifier = Modifier.weight(1f))
            IconInBox(icon = R.drawable.pencil_edit_02, onIconClick = {})

    }
}