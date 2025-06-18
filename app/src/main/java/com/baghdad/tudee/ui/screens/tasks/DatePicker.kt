package com.baghdad.tudee.ui.screens.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.ui.composable.DayChip
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime


@Composable
fun DatePicker(modifier: Modifier = Modifier) {
    val now = Clock.System.now()
    val today = now.toLocalDateTime(TimeZone.currentSystemDefault()).date

    val dates: List<LocalDate> = remember {
        (0..6).map { offset ->
            today.plus(DatePeriod(days = offset))

        }
    }
    var selectedDate by remember { mutableStateOf(today) }

    val monthAndYear = remember(selectedDate) {
        val monthName = selectedDate.month.name.lowercase()
            .replaceFirstChar { it.uppercase() }
        "$monthName ${selectedDate.year}"
    }
    TasksHeader(monthAndYear, modifier)

    HorizontalDatePicker(
        dates = dates,
        selectedDate = selectedDate,
        modifier = modifier
    )
}

@Composable
fun HorizontalDatePicker(
    dates: List<LocalDate>,
    selectedDate: LocalDate,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {

        LazyRow(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(dates) { date ->
                val isSelected = date == selectedDate

                DayChip(
                    dayNumber = date.dayOfMonth.toString(),
                    dayName = date.dayOfWeek.name.take(3),
                    isSelected = isSelected,
                )
            }
        }

    }
}