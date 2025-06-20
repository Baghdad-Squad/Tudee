package com.baghdad.tudee.ui.screens.categoryTasksScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.composable.CategoryTaskCard
import com.baghdad.tudee.ui.composable.TabItem
import com.baghdad.tudee.ui.composable.Tabs
import com.baghdad.tudee.ui.composable.categoryBottomSheet.EditCategoryBottomSheet
import com.baghdad.tudee.ui.screens.tasks.components.TasksEmptyScreen
import com.baghdad.tudee.ui.shared.Selectable
import com.baghdad.tudee.ui.utils.getCategoryIconPainter
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CategoryTasksScreen(
    categoryId: Long,
    viewModel: CategoryTasksViewModel = koinViewModel(parameters = { parametersOf(categoryId.toLong()) }),
    navigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CategoryTasksScreenContent(
        state = state,
        onTabSelected = viewModel::onTabSelected,
        isPredefinedCategory = state.isPredefinedCategory,
        onArrowBackClicked = { navigateBack() },
        onCategoryTitleChanged = { newTitle -> viewModel.onCategoryTitleChanged(newTitle) },
        onDeleteClick = { viewModel.onDeleteCategory() },
        onSaveButtonClick = { viewModel.onSaveCategoryChanges() }
    )
}

@Composable
private fun CategoryTasksScreenContent(
    state: CategoryTasksScreenUiState,
    onTabSelected: (Task.State) -> Unit,
    isPredefinedCategory: Boolean,
    onArrowBackClicked: () -> Unit,
    onCategoryTitleChanged: (String) -> Unit,
    onDeleteClick: () -> Unit,
    onSaveButtonClick: () -> Unit,
) {
    val pagerState = rememberPagerState(initialPage = state.selectedTab.ordinal) { 3 }
    var showEditCategoryDialog by remember { mutableStateOf(false) }
    val result = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        result.value = it
    }

    LaunchedEffect(state.selectedTab) {
        if (pagerState.currentPage != state.selectedTab.ordinal) {
            pagerState.animateScrollToPage(state.selectedTab.ordinal)
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

    Column(
        modifier = Modifier
            .background(Theme.color.surfaceColor.surface)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconInBox(icon = R.drawable.arrow_left_01, onIconClick = { onArrowBackClicked() })
            Text(
                text = state.categoryName,
                style = Theme.typography.title.large,
                color = Theme.color.textColor.title
            )
            if (!isPredefinedCategory) {
                Spacer(modifier = Modifier.weight(1f))
                IconInBox(icon = R.drawable.pencil_edit_02, onIconClick = {
                    showEditCategoryDialog = true
                })
            }
        }
        Tabs(
            selectableTabs = tabs,
            onTabSelected = { tab -> onTabSelected(tab.status) },
            modifier = Modifier.padding(bottom = 12.dp)
        )
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            val tasks = when (Task.State.entries[page]) {
                Task.State.TODO -> state.todoTasks
                Task.State.IN_PROGRESS -> state.inProgressTasks
                Task.State.DONE -> state.doneTasks
            }
            if (tasks.isEmpty()) {
                TasksEmptyScreen()
            } else {

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    items(tasks) { task ->
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
            EditCategoryBottomSheet(
                isVisible = showEditCategoryDialog,
                onDismiss = { showEditCategoryDialog = false },
                title = state.categoryName,
                onCategoryTitleChanged = onCategoryTitleChanged,
                onEditImageIconClick = {
                    launcher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                onSaveButtonClick = {
                    onSaveButtonClick()
                    showEditCategoryDialog = false
                },
                onDeleteClick = {
                    onDeleteClick()
                    showEditCategoryDialog = false
                },
                isLoading = state.isLoading,
                onCancelButtonClick = { showEditCategoryDialog = false },
                image = painterResource(R.drawable.ic_bug)
            )


        }
    }
}

@Composable
fun IconInBox(
    modifier: Modifier = Modifier,
    icon: Int, onIconClick: () -> Unit,
    tint: Color = Theme.color.textColor.body
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .border(
                width = 1.dp,
                shape = CircleShape,
                color = Theme.color.textColor.stroke
            )
            .clickable { onIconClick() }
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
}