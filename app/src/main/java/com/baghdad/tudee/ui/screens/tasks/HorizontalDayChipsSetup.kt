package com.baghdad.tudee.ui.screens.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.ui.composable.DayChip
import com.baghdad.tudee.ui.composable.dateYearDialog.DateDialog
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorizontalDayChipsSetup(
    uiState: TasksUiState,
    tasksInteractionHandler: TasksInteractionHandler,
    modifier: Modifier = Modifier
) {

    var showDatePicker by remember { mutableStateOf(false) }
    val selectedDate = uiState.selectedDate

    val dates: List<LocalDate> = uiState.monthDates



    TasksHeader(
        month = uiState.currentMonth.toString().take(3),
        year = uiState.currentYear.toString(),
        onNextArrowClicked = { tasksInteractionHandler.onNextMonthArrowClick() },
        onPreviousArrowClicked = { tasksInteractionHandler.onPreviousMonthArrowClick() },
        onDownArrowClicked = { showDatePicker = true },
        modifier = modifier
    )

    HorizontalDayChipsRow(
        dates = dates,
        selectedDate = selectedDate,
        tasksInteractionHandler = tasksInteractionHandler,
        modifier = modifier
    )

    if (showDatePicker) {

        DateDialog(
            initialValue = selectedDate?.atStartOfDayIn(TimeZone.currentSystemDefault())
                ?.toEpochMilliseconds() ?: 0L,
            isShowDatePicker = showDatePicker,
            onDismiss = { showDatePicker = false },
            onDateSelected = { millis ->
                showDatePicker = false
                millis?.let {
                    val date = kotlinx.datetime.Instant
                        .fromEpochMilliseconds(it)
                        .toLocalDateTime(TimeZone.currentSystemDefault())
                        .date
                    tasksInteractionHandler.onDatePickedFromDateDialog(date)
                }
            }
        )
    }
}

@Composable
fun HorizontalDayChipsRow(
    tasksInteractionHandler: TasksInteractionHandler,
    dates: List<LocalDate>,
    selectedDate: LocalDate?,
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
            contentPadding = PaddingValues(horizontal = 16.dp),
            state = LazyListState(firstVisibleItemIndex = if(dates.indexOf(selectedDate) < 2) 0 else dates.indexOf(selectedDate)-2)
        ) {
            items(dates) { date ->
                val isSelected = date == selectedDate
                DayChip(
                    dayNumber = date.dayOfMonth.toString(),
                    dayName = date.dayOfWeek.name.take(3),
                    isSelected = isSelected,
                    onSelected = {
                        tasksInteractionHandler.onDateSelectedFromHorizontalRow(date)
                    }
                )
            }
        }

    }
}