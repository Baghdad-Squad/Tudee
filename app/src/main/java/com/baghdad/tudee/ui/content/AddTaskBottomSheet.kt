package com.baghdad.tudee.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.data.service.CategoryServiceImpl
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.composable.CategoryItem
import com.baghdad.tudee.ui.composable.TaskPriority
import com.baghdad.tudee.ui.composable.TudeeTextField
import com.baghdad.tudee.ui.composable.button.PrimaryButton
import com.baghdad.tudee.ui.composable.button.SecondaryButton
import com.baghdad.tudee.ui.content.composable.PriorityButtonPart
import com.baghdad.tudee.ui.content.composable.TextFieldScreenPart
import com.baghdad.tudee.ui.fakeCategoriesData

@Composable
fun AddTaskBottomSheet(
    intial: Task? = null,
    category: CategoryServiceImpl
) {

    var titleText by remember { mutableStateOf("") }
    var paragraphText by remember { mutableStateOf("") }
    var dateTime by remember { mutableStateOf("") }

    Box() {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                TextFieldScreenPart(
                    title = intial?.title ?: titleText,
                    onTitleChange = { titleText = it },
                    paragraph = paragraphText,
                    onParagraphChange = { paragraphText = it },
                    dateTime = dateTime,
                    onDateTimeChange = { dateTime = it }
                )

                PriorityButtonPart()

                Column (
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Category",
                        style = Theme.typography.title.medium
                    )
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(145.dp),
            contentAlignment = Alignment.Center
        ){
            Column (
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ){
                val showButton by remember {
                    derivedStateOf {
                        titleText.isNotBlank() && paragraphText.isNotBlank() && dateTime.isNotBlank()
                    }
                }
                PrimaryButton(
                    label = "add",
                    onClick = {},
                    isLoading = false,
                    isEnabled = showButton,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                )
                SecondaryButton(
                    label = "Cancel",
                    onClick = {},
                    isLoading = false,
                    isEnabled = true,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}