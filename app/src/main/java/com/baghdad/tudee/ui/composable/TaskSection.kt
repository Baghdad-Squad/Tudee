package com.baghdad.tudee.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.screens.homeScreen.TextHeadTaskSection
import com.baghdad.tudee.ui.utils.getCategoryIconPainter

@Composable
fun TaskSection(
    title: String,
    tasks: List<Task>,
    categories: List<Category>,
    onTaskClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    if (tasks.isEmpty()) return

    Column(modifier = modifier.padding(top = 16.dp)) {

        TextHeadTaskSection(
            name = title,
            numberOfItem = tasks.size,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                )
        ) {}

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val taskPairs = tasks.chunked(2)

            itemsIndexed(taskPairs) { index, pair ->

                Column(modifier = Modifier.fillMaxWidth()) {
                    pair.forEach { task ->
                        val category = categories.firstOrNull { it.id == task.categoryId }
                        val icon = category?.image?.let { getCategoryIconPainter(it) }
                            ?: painterResource(R.drawable.ic_quran)

                        CategoryTaskCard(
                            title = task.title,
                            description = task.description,
                            priorityTask = task.priority,
                            icon = icon,
                            modifier = Modifier
                                .fillParentMaxWidth(0.95f)
                                .padding(bottom = 8.dp)
                        ) {
                            onTaskClick(task.id)
                        }
                    }
                }
            }
        }
    }
}
