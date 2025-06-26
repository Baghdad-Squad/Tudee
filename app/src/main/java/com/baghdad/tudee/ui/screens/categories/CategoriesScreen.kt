package com.baghdad.tudee.ui.screens.categories

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.components.AnimatedSnackbar
import com.baghdad.tudee.designSystem.components.SnackbarState
import com.baghdad.tudee.designSystem.components.button.FloatingActionButton
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.ui.base.EffectHandler
import com.baghdad.tudee.ui.navigation.LocalNavController
import com.baghdad.tudee.ui.navigation.Route
import com.baghdad.tudee.ui.screens.categories.component.CategoriesList
import com.baghdad.tudee.ui.screens.categories.component.CategoriesTopAppBar
import com.baghdad.tudee.ui.shared.components.TudeeScaffold
import com.baghdad.tudee.ui.shared.components.category.AddEditCategoryBottomSheet
import com.baghdad.tudee.ui.utils.image.uriToByteArray
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoriesScreen(
    viewModel: CategoriesViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarState by viewModel.snackbarState.collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    EffectHandler(
        effects = viewModel.effects,
        onNewEffect = { effect ->
            onNewEffect(effect, navController)
        }
    )
    CategoriesScreenContent(
        state = state,
        snackbarState = snackbarState,
        interactionListener = viewModel
    )
}

private fun onNewEffect(
    effect: CategoriesScreenEffect,
    navController: NavHostController
) {
    when (effect) {
        is CategoriesScreenEffect.NavigateToCategoryTasks -> navController.navigate(
            Route.CategoryTasksScreen(
                effect.categoryId
            )
        )
    }
}

@Composable
private fun CategoriesScreenContent(
    state: CategoriesScreenState,
    snackbarState: SnackbarState,
    interactionListener: CategoriesInteractionListener
) {
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uriToByteArray(context, uri)?.let {
                interactionListener.onUpdateCategoryImageByteArray(
                    it
                )
            }
        }
    TudeeScaffold(
        modifier = Modifier
            .background(Theme.color.surfaceColor.surface),
        topBar = {
            CategoriesTopAppBar()
        },
        floatingActionButton = {
            FloatingActionButton(
                painter = painterResource(id = R.drawable.ic_add_category),
                onClick = {
                    interactionListener.onAddCategoryClicked()
                }
            )
        },
        snackbar = {
            AnimatedSnackbar(
                snackbarState = snackbarState
            )
        }
    ) {
        CategoriesList(
            categories = state.categories,
            onCategoryClick = { interactionListener.onCategoryClicked(it.id) },
        )
        AddEditCategoryBottomSheet(
            state = state.addCategorySheetState,
            onCategoryTitleChanged = interactionListener::onUpdateCategoryTitle,
            onUploadIconClicked = {
                launcher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            },
            onDismiss = interactionListener::onDismissAddCategorySheet,
            onSaveClick = interactionListener::onAddCategory
        )
    }
}