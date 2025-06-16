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
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.baghdad.tudee.ui.composable.button.TextButton
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    datePickerState: DatePickerState,
    onDismiss: () -> Unit
) {
    val isShownEditIcon by remember { derivedStateOf { datePickerState.selectedDateMillis != null } }

    DatePickerDialog(
        modifier = Modifier.padding(horizontal = 12.dp),
        onDismissRequest = onDismiss,
        colors = DatePickerDefaults.colors(Theme.color.surfaceColor.surface),
        confirmButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(
                    label = stringResource(id = R.string.close_text),
                    onClick = onDismiss
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
        },
        dismissButton = {}
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
                val selectedDate = datePickerState.selectedDateMillis?.let { millis ->
                    Instant.fromEpochMilliseconds(millis)
                        .toLocalDateTime(TimeZone.currentSystemDefault())
                        .date
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, bottom = 8.dp, end = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    selectedDate?.let { date ->
                        AnimatedVisibility(visible = true) {
                            Text(
                                text = "${date.dayOfWeek.name.take(3).lowercase().replaceFirstChar { it.uppercase() }}, " +
                                        "${date.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${date.dayOfMonth}",
                                style = Theme.typography.headline.large
                            )
                        }
                    } ?: AnimatedVisibility(visible = !isShownEditIcon) {
                        Text(
                            text = stringResource(id = R.string.select_text),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                }
            }
        )
    }
}
