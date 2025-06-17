package com.baghdad.tudee.ui.content.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.ui.composable.TudeeTextField

@Composable
fun TextFieldScreenPart (){
    Text(
        text = "Add Task",
        style = Theme.typography.title.large
    )

    Spacer(modifier = Modifier.padding(12.dp))

    var titleText by remember { mutableStateOf("") }
    TudeeTextField(
        value = titleText,
        onValueChange = { titleText = it },
        hint = "Task title",
        leadingIcon = painterResource(id = R.drawable.ic_black_note),
    )

    var paragraphText by remember { mutableStateOf("") }

    TudeeTextField(
        value = paragraphText,
        onValueChange = { paragraphText = it },
        modifier = Modifier.padding(vertical = 12.dp),
        hint = "Description",
        height = 168,
        maxLines = 10
    )

    TudeeTextField(
        value = titleText,
        onValueChange = { titleText = it },
        hint = "22-6-2025",
        leadingIcon = painterResource(id = R.drawable.calendar_add),
    )
}