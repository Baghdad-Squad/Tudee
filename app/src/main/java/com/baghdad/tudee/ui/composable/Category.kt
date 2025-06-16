package com.baghdad.tudee.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.color.lightThemeColor
import com.baghdad.tudee.designSystem.textStyle.tudeeTextStyle
import com.baghdad.tudee.designSystem.theme.Theme

data class TaskItemData(
    val iconRes: Painter,
    val iconBackgroundColor: Color,
    val title: String,
    val description: String,
    val priority: String
)

@Composable
fun PriorityChip(text: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(
                color = Theme.color.status.yellowAccent,
                shape = CircleShape
            )
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    )
    {
        Icon(
            painter = painterResource(id = R.drawable.ic_alert),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = Theme.color.surfaceColor.surfaceHigh
        )
        Text(
            text = text,
            style = tudeeTextStyle.label.small,
            color = Theme.color.textColor.onPrimary,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun TaskInfo(title: String, description: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier
                .width(296.dp)
                .height(19.dp),
            color = Theme.color.textColor.body,
            style = tudeeTextStyle.body.medium,
            maxLines = 1,
        )
        Text(
            text = description,
            modifier = Modifier
                .fillMaxWidth(),
            color = Theme.color.textColor.title,
            style = tudeeTextStyle.label.small,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun TaskItem(item: TaskItemData, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .width(320.dp)
            .height(111.dp)
            .background(
                color = Theme.color.surfaceColor.surfaceHigh,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = Theme.color.surfaceColor.surfaceHigh,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(
                top = 4.dp,
                end = 12.dp,
                bottom = 12.dp,
                start = 4.dp
            ),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {

        TaskImage(
            item = item,
            modifier = Modifier.size(56.dp)
        )

        TaskInfo(
            title = item.title,
            description = item.description
        )
        PriorityChip(
            text = item.priority,
            modifier = Modifier
                .align(Alignment.End),
            )
    }
}

@Composable
fun TaskImage(item: TaskItemData, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .size(304.dp, 56.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Row(
            modifier = Modifier
                .offset(285.dp, 25.dp)
                .size(248.dp, 28.dp)
        ) {
            PriorityChip(text = item.priority)
        }
        Image(
            painter = painterResource(
                id = R.drawable.ic_pur_book
            ),
            contentDescription = item.title,
            modifier = Modifier.size(56.dp)
        )

    }
}

@Composable
fun TaskList() {
    val tasks = listOf(
        TaskItemData(
            painterResource(id = R.drawable.ic_bag),
            Theme.color.status.pinkAccent,
            "Organize Birthday Party",
            "Plan guest list and order cake...",
            "High"
        ),

        )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surfaceColor.surfaceHigh)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(tasks) { taskItem ->
            TaskItem(item = taskItem)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TaskListScreenPreview() {
    TaskList()
}