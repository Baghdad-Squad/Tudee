package com.baghdad.tudee.ui.addTask.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.ui.composable.TudeeTextField
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun TextFieldScreenPart (
    title: String,
    onTitleChange: (String) -> Unit,
    paragraph: String,
    onParagraphChange: (String) -> Unit,
    dateTime: String,
    onDateTimeChange: (String) -> Unit,

    ) {
    Column (Modifier.padding(horizontal = 16.dp))
    {
        Text(
            text = "Add Task",
            style = Theme.typography.title.large
        )

        Spacer(modifier = Modifier.padding(12.dp))

        TudeeTextField(
            value = title,
            onValueChange = onTitleChange,
            hint = "Task title",
            leadingIcon = painterResource(id = R.drawable.ic_black_note),
        )

        TudeeTextField(
            value = paragraph,
            onValueChange = onParagraphChange,
            modifier = Modifier.padding(vertical = 12.dp),
            hint = "Description",
            height = 168,
            maxLines = 10
        )

        TudeeTextField(
            value = dateTime,
            onValueChange = onDateTimeChange,
            hint = "22-6-2025",
            leadingIcon = painterResource(id = R.drawable.calendar_add),
            modifier = Modifier.clickable{}
        )
    }
}