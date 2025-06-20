package com.baghdad.tudee.ui.screens.category

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.ui.composable.SnakeBar
import com.baghdad.tudee.ui.composable.button.FloatingActionButton
import com.baghdad.tudee.ui.composable.categoryBottomSheet.AddCategoryBottomSheet
import com.baghdad.tudee.ui.screens.category.component.CategoryItems
import com.baghdad.tudee.ui.screens.category.component.CategoryScreenBar
import com.baghdad.tudee.ui.utils.image.uriToByteArray
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel = koinViewModel(),
    navigateToCategoryTask: (Long) -> Unit
) {
    val state by viewModel.statusValue.collectAsState()
    val addCategory: (CategoryUiState) -> Unit = { viewModel.addCategory(it) }
    CategoryScreenContent(state = state, addCategory, navigateToCategoryTask)
}

@Composable
fun CategoryScreenContent(
    state: List<CategoryUiState>,
    addCategory: (CategoryUiState) -> Unit,
    onCategoryClick: (Long) -> Unit
) {
    var showAddNewCategoryDialog by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    val result = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        result.value = it
    }
    val painter: Painter = rememberAsyncImagePainter(model = result.value)
    val context = LocalContext.current
    val isAdded = remember { mutableStateOf(false) }

    LaunchedEffect(isAdded.value) {
        if (isAdded.value) {
            delay(3000)
            isAdded.value = false
        }
    }


    Box(
        Modifier
            .fillMaxSize()
            .background(Theme.color.surfaceColor.surface)
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        Column() {
            CategoryScreenBar()
            CategoryItems(
                state = state,
                onCategoryClick = onCategoryClick
            )

        }

        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {
            Column(
                modifier = Modifier.align(Alignment.BottomEnd),
                horizontalAlignment = Alignment.End
            ) {
                FloatingActionButton(
                    painter = painterResource(
                        id = R.drawable.ic_add_category
                    ),
                    onClick = {
                        showAddNewCategoryDialog = true
                    },
                    modifier = Modifier.padding(bottom = 16.dp, end = 12.dp)

                )
            }
        }
        AddCategoryBottomSheet(
            isVisible = showAddNewCategoryDialog,
            onDismiss = {
                showAddNewCategoryDialog = false
                result.value = null
                text = ""
            },

            title = text,
            onCategoryTitleChanged = { text = it },

            isAddButtonEnabled = text.isNotBlank() && result.value != null,
            isCategoryImageUploaded = result.value != null,

            onUploadIconClicked = {
                launcher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            },

            onEditImageIconClick = {
                launcher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            },

            onAddButtonClick = {
                result.value?.let { uri ->
                    val imageBytes = uriToByteArray(context, uri)
                    if (imageBytes != null) {
                        addCategory(
                            CategoryUiState(
                                title = text,
                                image = Category.Image.ByteArray(imageBytes),
                            )
                        )
                    }
                }
                showAddNewCategoryDialog = false
                result.value = null
                isAdded.value = true

            },

            onCancelButtonClick = {
                showAddNewCategoryDialog = false
                result.value = null
                text = ""
            },

            image = painter,
            isLoading = false
        )

        SnakeBar(
            message = stringResource(R.string.added_category_successfully),
            isSuccess = true,
            isVisible = isAdded.value
        )

    }


}

