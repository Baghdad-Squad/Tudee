package com.baghdad.tudee.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.zIndex
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.ui.component.AddNewCategoryCard
import com.baghdad.tudee.ui.composable.BottomNavigation
import com.baghdad.tudee.ui.composable.CategoryItem
import com.baghdad.tudee.ui.composable.button.FloatingActionButton
import com.baghdad.tudee.ui.viewModel.CategoryViewModel
import com.baghdad.tudee.ui.viewModel.state.CategoryUiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel = koinViewModel()
) {
    val state by viewModel.statusValue.collectAsState()
    CategoryScreenContent(state = state)
}

@Composable
fun CategoryScreenContent(state: List<CategoryUiState>) {
    var showAddNewCategoryDialog by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    Box(Modifier
        .fillMaxSize()
        .background(Theme.color.surfaceColor.surface)) {
        Column() {
            // status bar
            Box(modifier = Modifier.height(40.dp).background(Theme.color.surfaceColor.surfaceHigh))
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
                verticalArrangement = Arrangement.spacedBy(25.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                    items(state) {
                        CategoryItem(
                            label = it.title,
                            icon = painterResource(id = it.imageUri.toInt()),
                            onClick = {

                            },
                            isSelected = false,
                            count = it.taskCount
                        )

                }

            }

        }


        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {
            Column(
                modifier = Modifier.align(Alignment.BottomCenter),
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

                BottomNavigation( // TODO : understand what should you do here with this composable
                    unSelectedIcon = {
                        1
                    },
                    selectedIcon = 2
                )
            }
        }

        AnimatedVisibility(
            visible = showAddNewCategoryDialog,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(1f)
            ) {
                AddNewCategoryCard(
                    onClickAdd = {},
                    onClickCancel = { showAddNewCategoryDialog = false },
                    onClickUpload = {},
                    onChangeText = { text = it },
                    modifier = Modifier
                        .background(color = Theme.color.surfaceColor.surface)
                        .padding(16.dp, 12.dp)
                        .align(Alignment.BottomCenter)
                )
            }
        }
    }
}
