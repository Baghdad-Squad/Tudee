package com.baghdad.tudee.ui.composable.dateYearDialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.atStartOfDayIn

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun DateDialog(
    initialValue: Long = 0L,
    isShowDatePicker: Boolean,
    onDismiss: () -> Unit,
    onDateSelected: (Long?) -> Unit
) {

    AnimatedVisibility(visible = isShowDatePicker) {
        val today = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date

        val initialDateMillis = today
            .atStartOfDayIn(TimeZone.currentSystemDefault())
            .toEpochMilliseconds()

        val selectedDateMillis = if (initialValue == 0L) initialDateMillis else initialValue

        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedDateMillis
        )
        TudeeDatePicker(
            onDateSelected = onDateSelected,
            datePickerState = datePickerState,
            onDismiss = onDismiss
        )
    }
}



