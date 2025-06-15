package com.baghdad.tudee.ui.composable.date_year_dialog

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateDialog() {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var selectedDateMillis by remember { mutableStateOf(datePickerState.selectedDateMillis) }


        if (showDatePicker) {
            DatePickerModal(onDateSelected = { millis ->
                selectedDateMillis = millis
            }, onDismiss = { showDatePicker = false })
        }
    }


