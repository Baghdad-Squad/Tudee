package com.baghdad.tudee.ui.screens.categoryTasksScreen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.base.EffectHandler
import com.baghdad.tudee.ui.composable.TabItem
import com.baghdad.tudee.ui.composable.Tabs
import com.baghdad.tudee.ui.composable.TasksEmptyScreen
import com.baghdad.tudee.ui.composable.bottomSheet.category.AddEditCategoryBottomSheet
import com.baghdad.tudee.ui.navigation.LocalNavController
import com.baghdad.tudee.ui.navigation.Route
import com.baghdad.tudee.ui.screens.categoryTasksScreen.components.CategoryTasksList
import com.baghdad.tudee.ui.screens.categoryTasksScreen.components.CategoryTasksScreenHeader
import com.baghdad.tudee.ui.shared.Selectable
import com.baghdad.tudee.ui.utils.image.uriToByteArray
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CategoryTasksScreen(
    categoryId: Long,
    viewModel: CategoryTasksViewModel = koinViewModel(parameters = { parametersOf(categoryId) }),
    navigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val navController = LocalNavController.current

    EffectHandler(
        effects = viewModel.effects,
        onNewEffect = { effect ->
            onNewEffect(effect, navController)
        }
    )
    CategoryTasksScreenContent(
        state = state,
        onArrowBackClicked = { navigateBack() },
        onToggleDeleteCategorySheet = { /*TODO: show delete category bottom sheet*/ },
        listener = viewModel,
    )
}

private fun onNewEffect(
    effect: CategoryTasksScreenEffect,
    navController: NavHostController
) {
    when (effect) {
        is CategoryTasksScreenEffect.OnCategoryDeleted -> navController.navigate(Route.CategoriesScreen)
    }
}


@Composable
private fun CategoryTasksScreenContent(
    state: CategoryTasksScreenUiState,
    onArrowBackClicked: () -> Unit,
    onToggleDeleteCategorySheet: () -> Unit,
    listener: CategoriesTasksInteractionListener
) {
    val context = LocalContext.current
    val pagerState = rememberPagerState(initialPage = state.selectedTab.ordinal) { 3 }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { it ->
            uriToByteArray(
                context,
                it
            )?.let { listener.onChangeImage(Category.Image.ByteArray(it)) }
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
            listener.onTabSelected(newTab)
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

        CategoryTasksScreenHeader(
            state = state,
            onArrowBackClicked = onArrowBackClicked,
            listener = listener
        )

        Tabs(
            selectableTabs = tabs,
            onTabSelected = { tab -> listener.onTabSelected(tab.status) },
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
                CategoryTasksList(
                    tasks = tasks,
                    state = state
                )
            }
            AddEditCategoryBottomSheet(
                state = state.addEditCategorySheetState,
                onCategoryTitleChanged = listener::onCategoryTitleChanged,
                onUploadIconClicked = {
                    launcher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                onDismiss = {
                    listener.onToggleEditCategorySheetVisibility()
                },
                onDeleteClick = onToggleDeleteCategorySheet,
                onSaveClick = listener::onSaveCategoryChanges,
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CategoryTasksScreenPreview() {
}
