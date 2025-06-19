package com.baghdad.tudee.ui.screens.homeScreen.addEditTask

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.screens.homeScreen.addEditTask.composable.MainButtonPart
import com.baghdad.tudee.ui.screens.homeScreen.addEditTask.composable.PriorityChipPart
import com.baghdad.tudee.ui.screens.homeScreen.addEditTask.composable.TextFieldScreenPart
import com.baghdad.tudee.ui.composable.CategoryItem
import com.baghdad.tudee.ui.composable.TudeeBottomSheet
import com.baghdad.tudee.ui.composable.button.PrimaryButton
import com.baghdad.tudee.ui.screens.homeScreen.addEditTask.composable.fakeCategoriesData
import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi


@OptIn(ExperimentalUuidApi::class)
@Composable
fun AddEditTaskBottomSheet(
    initial: Task? = null, // here we should add TaskUiState
    state: List<Category> // here we should add CategoryUiState
) {
    var titleText by remember { mutableStateOf(initial?.title ?:"") }
    var paragraphText by remember { mutableStateOf(initial?.description ?:"") }
    var dateTime by remember { mutableStateOf(initial?.date?:LocalDate(2027, 6, 22)) }
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
                PriorityChipPart (
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
                    icon = getCategoryIconPainter(category.image),
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
            AddEditTaskBottomSheet(
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

@Composable
fun getCategoryIconPainter(categoryImage: Category.Image): Painter {
    return when (val image = categoryImage) {
        is Category.Image.Predefined -> {
            val resId = when (image.type) {
                Category.PredefinedType.EDUCATION -> return painterResource(R.drawable.ic_book_open)
                Category.PredefinedType.SHOPPING -> return painterResource(R.drawable.ic_shopping_cart)
                Category.PredefinedType.MEDICAL -> return painterResource(R.drawable.ic_hospital_location)
                Category.PredefinedType.GYM -> return painterResource(R.drawable.ic_body_part_muscle)
                Category.PredefinedType.ENTERTAINMENT -> return painterResource(R.drawable.ic_baseball_bat)
                Category.PredefinedType.COOKING -> return painterResource(R.drawable.ic_chef)
                Category.PredefinedType.FAMILY_AND_FRIEND -> return painterResource(R.drawable.ic_user_multiple)
                Category.PredefinedType.TRAVELING -> return painterResource(R.drawable.ic_airplane)
                Category.PredefinedType.AGRICULTURE -> return painterResource(R.drawable.ic_green_plant)
                Category.PredefinedType.CODING -> return painterResource(R.drawable.ic_thinking_person)
                Category.PredefinedType.ADORATION -> return painterResource(R.drawable.ic_quran)
                Category.PredefinedType.FIXING_BUGS -> return painterResource(R.drawable.ic_bug)
                Category.PredefinedType.CLEANING -> return painterResource(R.drawable.ic_blush_brush)
                Category.PredefinedType.WORK -> return painterResource(R.drawable.ic_oranig_bag)
                Category.PredefinedType.BUDGETING -> return painterResource(R.drawable.ic_money_bag)
                Category.PredefinedType.SELF_CARE -> return painterResource(R.drawable.ic_in_love)
                Category.PredefinedType.EVENT -> return painterResource(R.drawable.ic_birthday_cake)
                else -> {
                    return painterResource(R.drawable.ic_birthday_cake)
                }
            }
            painterResource(id = resId)
        }

        is Category.Image.ByteArray -> {
            rememberAsyncImagePainter(
                model = image.data,
                placeholder = painterResource(R.drawable.image_add_02),
                error = painterResource(R.drawable.image_add_02)
            )
        }
    }
}