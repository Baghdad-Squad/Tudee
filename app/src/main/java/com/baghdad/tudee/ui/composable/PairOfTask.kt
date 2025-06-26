package com.baghdad.tudee.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.screens.homeScreen.HomeScreenUIState
import com.baghdad.tudee.ui.utils.getCategoryIconPainter
import com.baghdad.tudee.viewModel.homescreenViewModel.HomeScreenViewModel

@Composable
fun PairOfTask(
    modifier: Modifier = Modifier,
    pair: List<Task>, state: HomeScreenUIState, viewModel: HomeScreenViewModel,
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        CategoryTaskCard(
            title = pair[0].title,
            description = pair[0].description,
            priorityTask = pair[0].priority,
            icon = getCategoryIconPainter(state.categories.firstOrNull { it.id == pair[0].categoryId }!!.image),
            modifier = modifier

                .padding(bottom = 8.dp)
        ) {
            viewModel.getTaskDetailsById(id = pair[0].id)
        }

        if (pair.size > 1) {
            CategoryTaskCard(
                title = pair[1].title,
                description = pair[1].description,
                priorityTask = pair[1].priority,
                icon = getCategoryIconPainter(state.categories.firstOrNull { it.id == pair[1].categoryId }!!.image),
                modifier = modifier
            ) {
                viewModel.getTaskDetailsById(id = pair[0].id)
            }
        }
    }
}
