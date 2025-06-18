package com.baghdad.tudee.ui.composable.dateYearDialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerHeadline(datePickerState: DatePickerState){
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
        } ?: Text(
                text = stringResource(id = R.string.select_text),
                style = MaterialTheme.typography.titleLarge
            )
        }

    }

