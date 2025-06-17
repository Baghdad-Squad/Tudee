package com.baghdad.tudee.ui.composable.dateYearDialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.ui.composable.button.TextButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogActionButtons(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
    datePickerState: DatePickerState,
){
    Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 2.dp),
    horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(
            label = stringResource(id = R.string.clear_text),
            onClick = {
                datePickerState.selectedDateMillis = null
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        TextButton(
            label = stringResource(id = R.string.cancel_text),
            onClick = onDismiss
        )
        TextButton(
            label = stringResource(id = R.string.ok_text),
            onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }
        )
    }
}