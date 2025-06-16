package com.baghdad.tudee.ui.composable.dateYearDialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun DateDialog(
    isShowDatePicker: Boolean,
    onDismiss: () -> Unit,
    onDateSelected: (Long?) -> Unit

) {
    AnimatedVisibility(isShowDatePicker) {
        DatePickerModal(onDateSelected = { millis ->
            onDateSelected(millis)
        }, onDismiss = { onDismiss() })
    }
}


