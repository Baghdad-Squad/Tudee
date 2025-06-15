package com.baghdad.tudee.ui.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.designSystem.theme.TudeeTheme
import com.baghdad.tudee.ui.utils.noRippleClickable

@Composable
fun DayChip(
    dayNumber: String,
    dayName: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onSelected: () -> Unit = { },
) {

    val dayNumberColor by animateColorAsState(
        targetValue = if (isSelected) Theme.color.textColor.onPrimary else Theme.color.textColor.body,
        animationSpec = tween(durationMillis = 250)
    )

    val dayNameColor by animateColorAsState(
        targetValue = if (isSelected) Theme.color.textColor.onPrimaryCaption else Theme.color.textColor.hint,
        animationSpec = tween(durationMillis = 250)
    )

    Column(
        modifier = modifier
            .size(56.dp, 65.dp)
            .then(
                if (isSelected) {
                    Modifier.background(
                        brush = Theme.color.primaryColor.gradient,
                        shape = RoundedCornerShape(16.dp)

                    )
                } else Modifier.background(
                    color = Theme.color.surfaceColor.surface,
                    shape = RoundedCornerShape(16.dp)
                )
            )
            .noRippleClickable{
                onSelected()
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = dayNumber,
            style = Theme.typography.title.medium,
            color = dayNumberColor,
            modifier = Modifier.padding(bottom = 2.dp)
        )
        Text(
            text = dayName,
            style = Theme.typography.body.small,
            color = dayNameColor,
        )
    }


}

@Composable
@Preview
private fun DaysChipsPreview() {
    TudeeTheme {
        DayChip(dayNumber = "18", dayName = "Thu", isSelected = true)
    }
}