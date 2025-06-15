package com.baghdad.tudee.ui.composable
import androidx.annotation.DrawableRes
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baghdad.tudee.R

data class TaskItemData(
    @DrawableRes val iconRes: Int,
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
                color = Color(0xFFF2C849),
                shape = CircleShape
            )
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_alert),
            contentDescription = "alert Image for task ranking ",
            modifier = Modifier.size(16.dp),
        )
        Text(
            text = text,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
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
                .offset(x = 8.dp, y = 0.dp)
                .width(296.dp)
                .height(19.dp)
                .fillMaxWidth(),
            color = Color(0x991F1F1F),
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                lineHeight = 19.sp,
                letterSpacing = 0.sp
            )
        )
        Text(
            text = description,
            modifier = Modifier
                .offset(x = 8.dp, y = 0.dp)
                .fillMaxWidth(),
            color = Color(0xFF1F1F1F).copy(alpha = 0.6f),
            fontSize = 12.sp,
            textAlign = TextAlign.Start,
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
                color = Color(0xFFFFFFFF),
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = Color(0xFFFFFFFF),
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
        Row(
            modifier = modifier
                .size(304.dp, 56.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(304.dp, 56.dp),
                contentAlignment = Alignment.TopStart
            ) {
                Row(
                    modifier = Modifier
                        .size(248.dp, 28.dp)
                        .offset(x = 200.dp)
                ) {
                    PriorityChip(text = item.priority)
                }
                Image(
                    painter = painterResource(id = item.iconRes),
                    contentDescription = item.title,
                    modifier = Modifier.size(56.dp)
                )
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(2.dp),

                    modifier = Modifier
                        .offset(4.dp,62.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 8.dp)
                ) {
                    TaskInfo(title = item.title, description = item.description)
                }
            }
        }
    }
}


@Composable
fun TaskListScreen() {
    val tasks = listOf(
        TaskItemData(
            R.drawable.ic_pur_book,
            Color(0xFFF587A1),
            "Organize Birthday Party",
            "Plan guest list and order cake...",
            "High"
        ),

        )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
    TaskListScreen()
}