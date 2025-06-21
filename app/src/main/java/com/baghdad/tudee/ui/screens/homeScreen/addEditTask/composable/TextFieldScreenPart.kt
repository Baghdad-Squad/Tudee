package com.baghdad.tudee.ui.screens.homeScreen.addEditTask.composable

import androidx.compose.foundation.layout.Column
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
import com.baghdad.tudee.ui.composable.dateYearDialog.DateDialog
import com.baghdad.tudee.ui.utils.formatDate
import com.baghdad.tudee.ui.utils.millisToLocalDate
import com.baghdad.tudee.ui.utils.onClickTextField
import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun TextFieldScreenPart(
    title: String,
    onTitleChange: (String) -> Unit,
    paragraph: String,
    onParagraphChange: (String) -> Unit,
    dateTime: LocalDate,
    onDateChange: (LocalDate) -> Unit,
) {
    Column(Modifier.padding(horizontal = 16.dp))
    {
        var isDatePickerVisible by remember { mutableStateOf(false) }

        Text(
            text = "Add Task",
            style = Theme.typography.title.large.copy(Theme.color.textColor.title)
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
            value = dateTime.formatDate(),
            onValueChange = {},
            hint = "22-6-2025",
            readOnly = true,
            leadingIcon = painterResource(id = R.drawable.calendar_add),
            modifier = Modifier.onClickTextField(
                key = dateTime,
                onClick = {
                    isDatePickerVisible = true
                }
            )
        )

        DateDialog(
            isShowDatePicker = isDatePickerVisible,
            onDismiss = {
                isDatePickerVisible = false
            },
            onDateSelected = {
                isDatePickerVisible = false
                onDateChange(millisToLocalDate(it!!))
            }
        )


    }


}