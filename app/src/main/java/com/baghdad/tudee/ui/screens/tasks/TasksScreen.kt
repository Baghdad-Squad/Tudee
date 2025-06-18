package com.baghdad.tudee.ui.screens.tasks

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.ui.composable.BottomNavigation
import com.baghdad.tudee.ui.composable.CategoryTaskCard
import com.baghdad.tudee.ui.composable.button.FloatingActionButton

@Composable
fun TasksScreen(
    uiState: TasksUiState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            modifier =Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.color.surfaceColor.surface)
                    .padding(vertical = 20.dp)
            ) {
                Text(
                    text = "Tasks",
                    style = Theme.typography.title.large,
                    color = Theme.color.textColor.title,
                    modifier = Modifier.padding(bottom = 20.dp, start = 16.dp),
                )
                DatePicker(
                    modifier = Modifier.padding(bottom = 8.dp, start = 16.dp, end = 16.dp),
                )


                StatusTabs(
                    uiState = uiState,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                if(uiState.tasks.isNotEmpty()) {
                    TasksColumn(
                        uiState = uiState,
                    )
                }
                else{
                    TasksEmptyScreen()
                }

            }
            FloatingActionButton(
                onClick = { /*TODO*/ },
                painter = painterResource(id = R.drawable.ic_add),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(y = -(72).dp, x = -(12).dp)
            )
        }
        BottomNavigation(
            selectedIcon = 1,
            unSelectedIcon = {},
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun TasksEmptyScreen(modifier: Modifier = Modifier) {
    Column (
        modifier = modifier.fillMaxSize()
            .offset(y=-(68).dp)
            .padding(start = 12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 20.dp)
                    .align(Alignment.CenterEnd)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.no_tasks_image_container),
                    contentDescription = null,
                    modifier = Modifier.size(168.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.no_tasks_overlay),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.BottomEnd)
                        .size(152.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.progress_indicator),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterStart)

                )
                Image(
                    painter = painterResource(id = R.drawable.no_tasks_tudee),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
            Box(
                modifier = Modifier
                    .offset(y= (-40).dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomEnd = 2.dp,
                            bottomStart = 16.dp
                        )
                    )
                    .background(Theme.color.surfaceColor.surfaceHigh)
                    .padding(start = 12.dp, end = 8.dp)
                    .padding(vertical = 8.dp)
                    .align(Alignment.CenterStart)
                ,
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        text = "No tasks yet",
                        style = Theme.typography.title.small,
                        color = Theme.color.textColor.body
                    )
                    Text(
                        text = "Tap the + button to add your \n" +
                                "first one.",
                        style = Theme.typography.body.small,
                        color = Theme.color.textColor.hint
                    )
                }
            }

        }
    }
    
}


@Preview
@Composable
private fun TasksScreenPrev() {
    TasksScreen(
        uiState = TasksUiState(),
    )
}