package com.baghdad.tudee.ui.screens.categoryTasksScreen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.composable.CategoryTaskCard
import com.baghdad.tudee.ui.composable.TabItem
import com.baghdad.tudee.ui.composable.Tabs
import com.baghdad.tudee.ui.composable.bottomSheet.category.AddEditCategoryBottomSheet
import com.baghdad.tudee.ui.composable.TasksEmptyScreen
import com.baghdad.tudee.ui.shared.Selectable
import com.baghdad.tudee.ui.utils.getCategoryIconPainter
import com.baghdad.tudee.ui.utils.image.uriToByteArray
import com.baghdad.tudee.ui.utils.noRippleClickable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CategoryTasksScreen(
    categoryId: Long,
    viewModel: CategoryTasksViewModel = koinViewModel(parameters = { parametersOf(categoryId) }),
    navigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CategoryTasksScreenContent(
        state = state,
        onTabSelected = viewModel::onTabSelected,
        onArrowBackClicked = { navigateBack() },
        onCategoryTitleChanged = { newTitle -> viewModel.onCategoryTitleChanged(newTitle) },
        onCategoryImageChanged = { newImage -> viewModel.onChangeImage(newImage) },
        onDeleteCategory = { viewModel.onDeleteCategory() },
        onSaveButtonClick = { viewModel.onSaveCategoryChanges() },
        onToggleEditCategorySheet = { viewModel.toggleEditCategorySheetVisibility() },
        onToggleDeleteCategorySheet = { /*TODO: show delete category bottom sheet*/ }
    )
}

@Composable
private fun CategoryTasksScreenContent(
    state: CategoryTasksScreenUiState,
    onTabSelected: (Task.State) -> Unit,
    onArrowBackClicked: () -> Unit,
    onCategoryTitleChanged: (String) -> Unit,
    onCategoryImageChanged: (Category.Image) -> Unit,
    onToggleEditCategorySheet: () -> Unit,
    onToggleDeleteCategorySheet: () -> Unit,
    onDeleteCategory: () -> Unit,
    onSaveButtonClick: () -> Unit,
) {
    val context = LocalContext.current
    val pagerState = rememberPagerState(initialPage = state.selectedTab.ordinal) { 3 }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { it ->
            uriToByteArray(
                context,
                it
            )?.let { onCategoryImageChanged(Category.Image.ByteArray(it)) }
        }
    rememberAsyncImagePainter(model = launcher)

    LaunchedEffect(state.selectedTab) {
        if (pagerState.currentPage != state.selectedTab.ordinal) {
            pagerState.animateScrollToPage(state.selectedTab.ordinal)
        }
    }
    LaunchedEffect(pagerState.currentPage) {
        val newTab = Task.State.entries[pagerState.currentPage]
        if (state.selectedTab != newTab) {
            onTabSelected(newTab)
        }
    }

    val tabs =
        remember(state.selectedTab, state.todoTasks, state.inProgressTasks, state.doneTasks) {
            listOf(
                Selectable(
                    TabItem(
                        context.getString(R.string.in_progress),
                        state.inProgressTasks.size,
                        Task.State.IN_PROGRESS
                    ),
                    isSelected = state.selectedTab == Task.State.IN_PROGRESS
                ),
                Selectable(
                    TabItem(
                        context.getString(R.string.to_do),
                        state.todoTasks.size,
                        Task.State.TODO
                    ),
                    isSelected = state.selectedTab == Task.State.TODO
                ),
                Selectable(
                    TabItem(
                        context.getString(R.string.done),
                        state.doneTasks.size,
                        Task.State.DONE
                    ),
                    isSelected = state.selectedTab == Task.State.DONE
                )
            )
        }


    Column(
        modifier = Modifier
            .padding(WindowInsets.statusBars.asPaddingValues())
            .background(Theme.color.surfaceColor.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconInBox(icon = R.drawable.arrow_left_01, onIconClick = { onArrowBackClicked() })
            Text(
                text = state.category.title,
                style = Theme.typography.title.large,
                color = Theme.color.textColor.title,
                modifier = Modifier.padding(end = 16.dp)
            )
            if (!state.category.isPredefinedCategory) {
                Spacer(modifier = Modifier.weight(1f))
                IconInBox(icon = R.drawable.pencil_edit_02, onIconClick = {
                    onToggleEditCategorySheet()
                }
                )
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
                Task.State.IN_PROGRESS -> state.inProgressTasks
                Task.State.TODO -> state.todoTasks
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
                            icon = getCategoryIconPainter(categoryImage = state.category.image),
                            onClick = { /*TODO: Show task details bottom sheet*/ },
                            date = task.date.toString(),
                            showDate = true
                        )
                    }
                }
            }
            AddEditCategoryBottomSheet(
                state = state.addEditCategorySheetState,
                onCategoryTitleChanged = onCategoryTitleChanged,
                onUploadIconClicked = {
                    launcher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                onDismiss = {
                    onToggleEditCategorySheet()
                },
                onDeleteClick = onToggleDeleteCategorySheet,
                onSaveClick = onSaveButtonClick,
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
            .noRippleClickable { onIconClick() }
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
