package com.baghdad.tudee.ui.screens.categoryTasksScreen.components

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.composable.TasksEmptyScreen
import com.baghdad.tudee.ui.composable.bottomSheet.category.AddEditCategoryBottomSheet
import com.baghdad.tudee.ui.screens.categoryTasksScreen.CategoryTasksScreenUiState


@Composable
 fun CategoryTasksPager(
    pagerState: androidx.compose.foundation.pager.PagerState,
    state: CategoryTasksScreenUiState,
    onCategoryTitleChanged: (String) -> Unit,
    onUploadImage: () -> Unit,
    onDismissEditSheet: () -> Unit,
    onDeleteCategoryClick: () -> Unit,
    onSaveCategoryChanges: () -> Unit,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier
    ) { page ->
        val tasks = when (Task.State.entries[page]) {
            Task.State.IN_PROGRESS -> state.inProgressTasks
            Task.State.TODO -> state.todoTasks
            Task.State.DONE -> state.doneTasks
        }

        if (tasks.isEmpty()) {
            TasksEmptyScreen()
        } else {
            CategoryTasksList(tasks = tasks, state = state)
        }

        AddEditCategoryBottomSheet(
            state = state.addEditCategorySheetState,
            onCategoryTitleChanged = onCategoryTitleChanged,
            onUploadIconClicked = onUploadImage,
            onDismiss = onDismissEditSheet,
            onDeleteClick = onDeleteCategoryClick,
            onSaveClick = onSaveCategoryChanges
        )
    }
}
