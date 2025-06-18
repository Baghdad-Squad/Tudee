package com.baghdad.tudee.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.ui.composable.CategoryItem
import com.baghdad.tudee.ui.composable.button.FloatingActionButton
import com.baghdad.tudee.ui.composable.categoryBottomSheet.AddCategoryBottomSheet
import com.baghdad.tudee.ui.viewModel.CategoryViewModel
import com.baghdad.tudee.ui.viewModel.state.CategoryUiState
import org.koin.androidx.compose.koinViewModel
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel = koinViewModel()
) {
    val state by viewModel.statusValue.collectAsState()
    val addCategory: (CategoryUiState) -> Unit = { viewModel.addCategory(it) }
    CategoryScreenContent(state = state, addCategory)
}

@OptIn(ExperimentalUuidApi::class)
@Composable
fun CategoryScreenContent(
    state: List<CategoryUiState>,
    addCategory: (CategoryUiState) -> Unit
) {
    var showAddNewCategoryDialog by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    Box(
        Modifier
            .fillMaxSize()
            .background(Theme.color.surfaceColor.surface)
    ) {
        Column() {
            // status bar
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .background(Theme.color.surfaceColor.surfaceHigh)
            )
            // screen bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(Theme.color.surfaceColor.surfaceHigh)
                    .padding(16.dp, 20.dp)

            ) {
                Text(
                    text = "Categories",
                    modifier = Modifier.align(Alignment.CenterStart),
                    style = Theme.typography.title.large,
                    color = Theme.color.textColor.title
                )
            }
            LazyVerticalGrid(
                columns = GridCells.Adaptive(104.dp),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = (56 + 12).dp
                ),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state) {
                    CategoryItem(
                        label = it.title,
                        icon = painterResource(id = it.imageUri.toInt()),
                        onClick = {

                        },
                        isSelected = false,
                        count = it.taskCount,
                    )

                }

            }

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
                        id = R.drawable.resources_add
                    ),
                    onClick = {
                        showAddNewCategoryDialog = true
                    },
                    modifier = Modifier.padding(bottom = 17.dp, end = 12.dp)
                )
                Spacer(Modifier.height(58.dp))
            }
        }
        AddCategoryBottomSheet(
            isVisible = showAddNewCategoryDialog,
            onDismissRequest = { showAddNewCategoryDialog = false },
            title = text,
            onValueChange = { text = it },
            showButton = !text.isBlank(),
            imageUploaded = true,
            onUploadClick = {},
            onEditImageIconClick = {},

            onAddButtonClick = {
                addCategory(
                    CategoryUiState(
                        id = Uuid.random(),
                        title = text,
                        imageUri = "",
                        taskCount = 0,
                    )
                )
            },
            onCanceleButtonClick = { showAddNewCategoryDialog = false },

            )
    }
}
