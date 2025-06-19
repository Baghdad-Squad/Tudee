import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.designSystem.theme.TudeeTheme
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.utils.noRippleClickable


@Composable
fun TaskPriority(
    priorityTask: Task.Priority,
    isClickable: Boolean,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val priorityProperties = when (priorityTask) {
        Task.Priority.HIGH -> PriorityProperties(
            Theme.color.status.pinkAccent,
            R.drawable.ic_flag,
            "High"
        )

        Task.Priority.MEDIUM -> PriorityProperties(
            Theme.color.status.yellowAccent,
            R.drawable.ic_alert,
            "Medium"
        )

        Task.Priority.LOW -> PriorityProperties(
            Theme.color.status.greenAccent,
            R.drawable.ic_trade_down,
            "Low"
        )
    }

    val isClicked = remember { mutableStateOf(false) }

    val backgroundColor by animateColorAsState(
        targetValue = if (isClicked.value) priorityProperties.color
        else Theme.color.surfaceColor.surfaceLow
    )

    val contentColor by animateColorAsState(
        if (isClicked.value) Theme.color.textColor.onPrimary
        else Theme.color.textColor.hint
    )


    Row(
        modifier = modifier
            .clip(shape = RoundedCornerShape(100))
            .background(backgroundColor)
            .then(
                if (isClickable) {
                    Modifier
                        .noRippleClickable {
                            if (onClick != null) {
                                onClick()
                            }
                            isClicked.value = !isClicked.value
                        }
                } else Modifier
            )
            .padding(vertical = 6.dp, horizontal = 8.dp),

        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(priorityProperties.iconRes),
            contentDescription = "Priority of task is ${priorityTask.name}",
            tint = contentColor
        )
        Text(
            text = priorityProperties.text,
            style = Theme.typography.label.small,
            color = contentColor
        )
    }
}

private data class PriorityProperties(
    val color: Color,
    @DrawableRes val iconRes: Int,
    val text: String
)

@Composable
@Preview
private fun PriorityPreview() {
    TudeeTheme {
        TaskPriority(
            priorityTask = Task.Priority.HIGH,
            isClickable = true,
            onClick = {}
        )
    }
}
