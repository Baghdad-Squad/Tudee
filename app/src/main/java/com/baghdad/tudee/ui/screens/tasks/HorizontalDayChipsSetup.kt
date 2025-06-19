package com.baghdad.tudee.ui.screens.tasks

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.ui.composable.DayChip
import com.baghdad.tudee.ui.composable.dateYearDialog.DateDialog
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
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
        modifier = modifier.padding(horizontal = 12.dp)
    )

    AnimatedContent(
        targetState = dates,
        transitionSpec = {
            if (targetState.first() > initialState.first()) {
                slideInHorizontally { it } + fadeIn() with slideOutHorizontally { -it } + fadeOut()
            } else {
                slideInHorizontally { -it } + fadeIn() with slideOutHorizontally { it } + fadeOut()
            }
        }
    ) {
        HorizontalDayChipsRow(
            dates = it,
            selectedDate = selectedDate,
            tasksInteractionHandler = tasksInteractionHandler,
            modifier = modifier
        )
    }

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