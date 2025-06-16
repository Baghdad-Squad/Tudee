package com.baghdad.tudee.ui.composable.dateYearDialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import kotlinx.datetime.Clock


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateDialog(
    initialValue: Long = 0L,
    isShowDatePicker: Boolean,
    onDismiss: () -> Unit,
    onDateSelected: (Long?) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis =if(initialValue==0L) Clock.System.now().toEpochMilliseconds() else  initialValue*1000L
    )
    AnimatedVisibility(isShowDatePicker) {
        DatePickerModal(onDateSelected = { millis ->
            onDateSelected(millis)
        }, datePickerState = datePickerState,
            onDismiss = { onDismiss() })
    }
}


