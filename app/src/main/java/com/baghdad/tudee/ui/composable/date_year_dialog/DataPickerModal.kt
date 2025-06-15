package com.baghdad.tudee.ui.composable.date_year_dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import java.time.Instant
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit, onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    var showEditIcon by remember { mutableStateOf(false) }

    LaunchedEffect(datePickerState.selectedDateMillis) {
        showEditIcon = datePickerState.selectedDateMillis != null
    }

    DatePickerDialog(modifier = Modifier.padding(horizontal = 21.dp),
        onDismissRequest = onDismiss,
        colors = DatePickerDefaults.colors(Theme.color.primaryColor.variant),
        confirmButton = {
            Row(horizontalArrangement = Arrangement.Start) {
                TextButton(onClick = onDismiss) {
                    Text(
                        stringResource(id = R.string.clear_text),
                        color = Theme.color.primaryColor.normal
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(
                            text = stringResource(id = R.string.cancel_text),
                            color = Theme.color.primaryColor.normal
                        )
                    }
                    TextButton(onClick = {
                        onDateSelected(datePickerState.selectedDateMillis)
                        onDismiss()
                    }) {
                        Text(
                            stringResource(id = R.string.ok_text),
                            color = Theme.color.primaryColor.normal
                        )
                    }
                }
            }
        },
        dismissButton = {}) {
        Column {
            DatePicker(state = datePickerState,
                showModeToggle = false,
                colors = DatePickerDefaults.colors(
                    containerColor = Color.White,
                    selectedDayContainerColor = Theme.color.primaryColor.normal,
                    todayDateBorderColor = Theme.color.primaryColor.normal,
                    selectedYearContainerColor = Theme.color.primaryColor.normal
                ),
                headline = {
                    val selectedDate = datePickerState.selectedDateMillis?.let {
                        Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 24.dp, bottom = 8.dp, end = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (selectedDate != null) {
                            Text(
                                text = "${
                                    selectedDate.dayOfWeek.getDisplayName(
                                        TextStyle.SHORT, Locale.getDefault()
                                    )
                                }, ${
                                    selectedDate.month.getDisplayName(
                                        TextStyle.FULL, Locale.getDefault()
                                    )
                                } ${selectedDate.dayOfMonth}",
                                style = Theme.typography.headline.large
                            )
                        } else {
                            Text(
                                text = stringResource(id = R.string.select_text),
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                        if (showEditIcon) {
                            Icon(painter = painterResource(id = R.drawable.ic_black_edit),
                                contentDescription = stringResource(id = R.string.edit_text),
                                modifier = Modifier.clickable { })
                        }
                    }
                })
        }
    }
}