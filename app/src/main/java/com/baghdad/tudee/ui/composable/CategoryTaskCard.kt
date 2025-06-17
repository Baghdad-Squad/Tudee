
package com.baghdad.tudee.ui.composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.utils.noRippleClickable

@Composable
fun CategoryTaskCard(
    title: String,
    description: String,
    priorityTask: Task.Priority,
    icon: Painter,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.surfaceColor.surfaceHigh, shape = RoundedCornerShape(16.dp))
            .padding( 12.dp)
            .noRippleClickable {
                onClick()
            }
    ) {
        Row(
            Modifier
                .fillMaxWidth()
            ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(Modifier.size(56.dp), contentAlignment = Alignment.Center) {
                Image(
                    painter = icon,
                    contentDescription = "Category Icon",
                )
            }
            TaskPriority(
                priorityTask = priorityTask,
            )
        }

        Column(modifier = Modifier.padding(top = 2.dp)) {
            Text(
                text = title,
                style = Theme.typography.label.large,
                color = Theme.color.textColor.body,
            )
            Text(
                text = description,
                style = Theme.typography.label.small,
                color = Theme.color.textColor.hint,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

