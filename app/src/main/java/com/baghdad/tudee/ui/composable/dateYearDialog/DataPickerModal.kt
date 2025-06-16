package com.baghdad.tudee.ui.composable.dateYearDialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
    val isShownEditIcon by remember { derivedStateOf { datePickerState.selectedDateMillis != null } }

    DatePickerDialog(modifier = Modifier.padding(horizontal = 12.dp),
        onDismissRequest = onDismiss,
        colors = DatePickerDefaults.colors(Theme.color.surfaceColor.surface),
        confirmButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = onDismiss) {
                    Text(
                        stringResource(id = R.string.close_text),
                        color = Theme.color.primaryColor.normal,
                        style = Theme.typography.label.large
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = onDismiss) {
                    Text(
                        text = stringResource(id = R.string.cancel_text),
                        color = Theme.color.primaryColor.normal,
                        style = Theme.typography.label.large
                    )
                }
                TextButton(onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
                }) {
                    Text(
                        stringResource(id = R.string.ok_text),
                        color = Theme.color.primaryColor.normal,
                        style = Theme.typography.label.large
                    )
                }
            }
        },
        dismissButton = {}) {
        DatePicker(state = datePickerState,
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
                val selectedDate = datePickerState.selectedDateMillis?.let {
                    Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, bottom = 8.dp, end = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    selectedDate?.let { date ->
                        AnimatedVisibility(true) {
                            Text(
                                text = "${
                                    date.dayOfWeek.getDisplayName(
                                        TextStyle.SHORT, Locale.getDefault()
                                    )
                                }, ${
                                    date.month.getDisplayName(
                                        TextStyle.FULL, Locale.getDefault()
                                    )
                                } ${date.dayOfMonth}", style = Theme.typography.headline.large
                            )
                        }
                    } ?: AnimatedVisibility(visible = !isShownEditIcon) {
                        Text(
                            text = stringResource(id = R.string.select_text),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    AnimatedVisibility(isShownEditIcon) {
                        Icon(painter = painterResource(id = R.drawable.ic_black_edit),
                            contentDescription = stringResource(id = R.string.edit_text),
                            modifier = Modifier.clickable { })
                    }
                }
            })
    }
}

