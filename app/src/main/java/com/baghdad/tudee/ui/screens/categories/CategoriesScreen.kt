package com.baghdad.tudee.ui.screens.categories

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.ui.base.EffectHandler
import com.baghdad.tudee.ui.composable.AnimatedSnackbar
import com.baghdad.tudee.ui.composable.SnackbarState
import com.baghdad.tudee.ui.composable.SnakeBar
import com.baghdad.tudee.ui.composable.TudeeScaffold
import com.baghdad.tudee.ui.composable.bottomSheet.category.AddEditCategoryBottomSheet
import com.baghdad.tudee.ui.composable.button.FloatingActionButton
import com.baghdad.tudee.ui.screens.categories.component.CategoryItems
import com.baghdad.tudee.ui.screens.categories.component.CategoryScreenBar
import com.baghdad.tudee.ui.utils.image.uriToByteArray
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoriesScreen(
    viewModel: CategoriesViewModel = koinViewModel(),
    navigateToCategoryTask: (Long) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarState by viewModel.snackbarState.collectAsStateWithLifecycle()
    EffectHandler(
        effects = viewModel.effects
    ) { effect ->
        when (effect) {
            is CategoriesUiEffect.NavigateToCategoryTasks -> navigateToCategoryTask(effect.categoryId)
        }
    }
    CategoriesScreenContent(
        state = state,
        snackbarState = snackbarState,
        interactionListener = viewModel
    )
}

@Composable
private fun CategoriesScreenContent(
    state: CategoriesUiState,
    snackbarState: SnackbarState,
    interactionListener: CategoriesInteractionListener
) {
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uriToByteArray(context, uri)?.let { interactionListener.onUpdateCategoryImage(it) }
        }
    TudeeScaffold(
        modifier = Modifier
            .background(Theme.color.surfaceColor.surface),
        topBar = {
            CategoryScreenBar()
        },
        floatingActionButton = {
            FloatingActionButton(
                painter = painterResource(
                    id = R.drawable.ic_add_category
                ),
                onClick = {
                    interactionListener.onToggleAddCategorySheetVisibility()
                }
            )
        },
        snackbar = {
            AnimatedSnackbar(
                snackbarState = snackbarState
            )
        }
    ) {
        CategoryItems(
            state = state.categories,
            onCategoryClick = interactionListener::onCategoryClick
        )
        AddEditCategoryBottomSheet(
            state = state.addCategorySheetState,
            onCategoryTitleChanged = interactionListener::onUpdateCategoryTitle,
            onUploadIconClicked = {
                launcher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            },
            onDismiss = {
                interactionListener.onToggleAddCategorySheetVisibility()
            },
            onSaveClick = interactionListener::onAddCategory
        )
    }
}