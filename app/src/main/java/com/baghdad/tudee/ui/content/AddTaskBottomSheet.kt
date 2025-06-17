package com.baghdad.tudee.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.composable.TaskPriority
import com.baghdad.tudee.ui.composable.TudeeTextField
import com.baghdad.tudee.ui.content.composable.TextFieldScreen

@Preview(showBackground = true)
@Composable
fun AddTaskBottomSheet() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
    ) {
        item {
            TextFieldScreen()

            Column(modifier = Modifier.fillMaxWidth()
                .padding(vertical = 16.dp)) {
                Text(
                    text = "Priority",
                    style = Theme.typography.title.medium
                )
                Row(modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)){
                    TaskPriority(
                        priorityTask = Task.Priority.HIGH,
                    )
                    TaskPriority(
                        priorityTask = Task.Priority.MEDIUM,
                    )
                    TaskPriority(
                        priorityTask = Task.Priority.LOW,
                    )
                }
            }
        }
    }
}