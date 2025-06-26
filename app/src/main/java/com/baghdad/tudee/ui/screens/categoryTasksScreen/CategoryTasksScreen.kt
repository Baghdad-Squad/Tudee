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
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.base.EffectHandler
import com.baghdad.tudee.ui.composable.Tabs
import com.baghdad.tudee.ui.composable.delete_item.ShowDeleteCategorySheet
import com.baghdad.tudee.ui.navigation.LocalNavController
import com.baghdad.tudee.ui.navigation.Route
import com.baghdad.tudee.ui.screens.categoryTasksScreen.components.CategoryTasksPager
import com.baghdad.tudee.ui.screens.categoryTasksScreen.components.CategoryTasksScreenHeader
import com.baghdad.tudee.ui.screens.categoryTasksScreen.components.rememberTabs
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
        listener = viewModel,
    )
}

private fun onNewEffect(
    effect: CategoryTasksScreenEffect,
    navController: NavHostController
) {
    when (effect) {
        is CategoryTasksScreenEffect.NavigateToCategoriesScreen -> navController.navigate(Route.CategoriesScreen)
    }
}


@Composable
private fun CategoryTasksScreenContent(
    state: CategoryTasksScreenUiState,
    onArrowBackClicked: () -> Unit,
    listener: CategoriesTasksInteractionListener
) {
    val context = LocalContext.current
    val pagerState = rememberPagerState(initialPage = state.selectedTab.ordinal) { 3 }
    val imageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { it ->
            uriToByteArray(
                context,
                it
            )?.let { listener.onChangeImage(Category.Image.ByteArray(it)) }
        }
    rememberAsyncImagePainter(model = imageLauncher)

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
        val tabs = rememberTabs(state, context)

        Tabs(
            selectableTabs = tabs,
            onTabSelected = { tab -> listener.onTabSelected(tab.status) },
            modifier = Modifier.padding(bottom = 12.dp)
        )

        CategoryTasksPager(
            pagerState = pagerState,
            state = state,
            onCategoryTitleChanged = listener::onCategoryTitleChanged,
            onUploadImage = {
                imageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            },
            onDismissEditSheet = listener::onToggleEditCategorySheetVisibility,
            onDeleteCategoryClick = listener::toggleDeleteCategorySheet,
            onSaveCategoryChanges = listener::onSaveCategoryChanges
        )
        ShowDeleteCategorySheet(
            isVisible = state.showDeleteCategorySheet,
            onDeleteConfirmed = {
                listener.onDeleteCategory()
                listener.toggleDeleteCategorySheet()
            },
            onCancelConfirmed = {
                listener.toggleDeleteCategorySheet()
            }
        )


    }
}


@Preview(showBackground = true)
@Composable
fun CategoryTasksScreenPreview() {
}
