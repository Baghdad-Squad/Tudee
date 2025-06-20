package com.baghdad.tudee.ui.composable


import TaskPriority
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.designSystem.theme.TudeeTheme
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.utils.noRippleClickable

@Composable
fun CategoryTaskCard(
    title: String,
    description: String,
    date: String ="",
    priorityTask: Task.Priority,
    icon: Painter,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    showDate: Boolean = false
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.surfaceColor.surfaceHigh, shape = RoundedCornerShape(16.dp))
            .padding(12.dp)
            .noRippleClickable {
                onClick()
            }
    ) {
        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(Modifier.size(56.dp), contentAlignment = Alignment.Center) {
                Image(
                    painter = icon,
                    contentDescription = "Category Icon",
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                if (showDate) {
                    Box(
                        modifier = Modifier.background(
                            color = Theme.color.surfaceColor.surface,
                            shape = CircleShape
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.calendar_favorite_01),
                                contentDescription = stringResource(
                                    R.string.calendar_favorite
                                )
                            )
                            Text(
                                text = date,
                                style = Theme.typography.label.small,
                                color = Theme.color.textColor.body,
                            )

                        }
                    }
                }
                TaskPriority(
                    priorityTask = priorityTask,
                )
            }


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