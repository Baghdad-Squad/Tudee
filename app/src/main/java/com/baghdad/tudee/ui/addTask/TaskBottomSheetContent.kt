package com.baghdad.tudee.ui.addTask

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.data.fakeData.fakeCategoriesData
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.addTask.composable.MainButtonPart
import com.baghdad.tudee.ui.addTask.composable.PriorityChipPart
import com.baghdad.tudee.ui.addTask.composable.TextFieldScreenPart
import com.baghdad.tudee.ui.composable.CategoryItem
import com.baghdad.tudee.ui.composable.TudeeBottomSheet
import com.baghdad.tudee.ui.composable.button.PrimaryButton
import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@OptIn(ExperimentalUuidApi::class)
@Composable
fun TaskBottomSheet(
    initial: Task? = null, // here we should add TaskUiState
    state: List<Category> // here we should add CategoryUiState
) {
    var titleText by remember { mutableStateOf(initial?.title ?:"") }
    var paragraphText by remember { mutableStateOf(initial?.description ?:"") }
    var dateTime by remember { mutableStateOf(initial?.date?:LocalDate(2023, 6, 22)) }
    var selectedCategoryId by remember { mutableStateOf(initial?.categoryId) }
    var selectedPriority by remember { mutableStateOf(initial?.priority) }


    Column(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(104.dp),
            modifier = Modifier
                .weight(1f)
,            contentPadding = PaddingValues(
                top = 16.dp,
                bottom = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(25.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            userScrollEnabled = true
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                TextFieldScreenPart(
                    title = titleText,
                    onTitleChange = { titleText = it },
                    paragraph = paragraphText,
                    onParagraphChange = { paragraphText = it },
                    dateTime = dateTime,
                    onDateChange = { dateTime = it }
                )
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                PriorityChipPart(
                    selectedPriority = selectedPriority,
                    onPrioritySelected = { selectedPriority = it }
                )
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = "Category",
                    style = Theme.typography.title.medium,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            items(state) { category ->
                CategoryItem(
                    label = category.title,
                    icon = painterResource(id = category.imageUri.toInt()),
                    onClick = {
                        selectedCategoryId = if (selectedCategoryId == category.id) null else category.id
                    },
                    isSelected = selectedCategoryId == category.id
                )
            }
        }

        val showButton by remember {
            derivedStateOf {
                titleText.isNotBlank() && paragraphText.isNotBlank()
            }
        }
        MainButtonPart(showButton , initial?.title)
    }
}

@OptIn(ExperimentalUuidApi::class)
@Composable
fun ShowTaskSheetButton() {
    var showSheet by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {
        PrimaryButton(
            onClick = { showSheet = true },
            label = "open",
            modifier = Modifier.align(Alignment.Center)
        )
        TudeeBottomSheet(
            isVisible = showSheet,
            onDismiss = { showSheet = false }
        ) {
            TaskBottomSheet(
                initial = Task(
                    id = 1,
                    title = "Nice chance",
                    description = "sdfsd",
                    date = LocalDate(2023, 6, 7),
                    priority = Task.Priority.HIGH,
                    categoryId = 1,
                    state = Task.State.TODO
                ),
                state = fakeCategoriesData()
            )
        }
    }
}
// on dismiss and on saved should be called

@Preview(showBackground = true)
@Composable
fun MyPreview(){
    ShowTaskSheetButton()
}