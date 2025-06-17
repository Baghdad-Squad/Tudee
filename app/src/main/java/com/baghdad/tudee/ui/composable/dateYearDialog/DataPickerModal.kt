package com.baghdad.tudee.ui.composable.dateYearDialog
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TudeeDatePicker(
    onDateSelected: (Long?) -> Unit,
    datePickerState: DatePickerState,
    onDismiss: () -> Unit
) {
    DatePickerDialog(
        modifier = Modifier.padding(horizontal = 12.dp),
        onDismissRequest = onDismiss,
        colors = DatePickerDefaults.colors(Theme.color.surfaceColor.surface),
        confirmButton = {
            DialogActionButtons(
                onDateSelected = onDateSelected,
                onDismiss = onDismiss,
                datePickerState = datePickerState
            )
        },
    ) {
        DatePicker(
            state = datePickerState,
            showModeToggle = false,
            colors = DatePickerDefaults.colors(
                containerColor = Theme.color.surfaceColor.surface,
                selectedDayContainerColor = Theme.color.primaryColor.normal,
                todayDateBorderColor = Theme.color.primaryColor.normal,
                todayContentColor = Theme.color.primaryColor.normal,
                selectedYearContainerColor = Theme.color.primaryColor.normal,
                selectedDayContentColor = Theme.color.textColor.onPrimary,
                weekdayContentColor = Theme.color.textColor.title,
                titleContentColor = Theme.color.textColor.title,
                headlineContentColor = Theme.color.textColor.title,
                yearContentColor = Theme.color.textColor.title,
                navigationContentColor = Theme.color.textColor.title
            ),
            headline = {
              DatePickerHeadline(datePickerState = datePickerState)
            }
        )
    }
}
