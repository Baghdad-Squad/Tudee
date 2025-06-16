package com.baghdad.tudee.ui.composable.dateYearDialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateDialog(
    isShowDatePicker: Boolean,
    onDismiss: () -> Unit,
    onDateSelected: (Long?) -> Unit
) {
    val datePickerState = remember {
        DatePickerState(
            locale = Locale.getDefault()
        )
    }
    AnimatedVisibility(isShowDatePicker) {
        DatePickerModal(onDateSelected = { millis ->
            onDateSelected(millis)
        }, datePickerState = datePickerState,
            onDismiss = { onDismiss() })
    }
}


