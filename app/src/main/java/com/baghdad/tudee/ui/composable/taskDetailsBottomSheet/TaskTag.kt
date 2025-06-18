package com.baghdad.tudee.ui.composable.taskDetailsBottomSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.domain.entity.Task

@Composable
fun TaskState(
    taskState: Task.State,
    modifier: Modifier = Modifier,
) {
    val StateProperties = when (taskState) {
        Task.State.TODO -> StateProperties(
            Theme.color.status.yellowVariant,
            "To-Do",
            Theme.color.status.yellowAccent
        )

        Task.State.IN_PROGRESS -> StateProperties(
            Theme.color.status.purpleVariant,
            "in progress",
            Theme.color.status.purpleAccent
        )

        Task.State.DONE -> StateProperties(
            Theme.color.status.greenVariant,
            "Done",
            Theme.color.status.greenAccent
        )
    }
    Row(
        modifier = modifier
            .background(
                StateProperties.backgroundColor,
                shape = RoundedCornerShape(100)
            )
            .padding(vertical = 6.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(5.dp)
                .background(
                    color = StateProperties.textColor,
                    shape = CircleShape
                )
        )
        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = StateProperties.text,
            style = Theme.typography.label.small,
            color = StateProperties.textColor
        )
    }

}

private data class StateProperties(
    val backgroundColor: Color,
    val text: String,
    val textColor: Color
)

@Preview(showBackground = true)
@Composable
fun TaskTagPreview() {
    TaskState(taskState = Task.State.IN_PROGRESS)
}