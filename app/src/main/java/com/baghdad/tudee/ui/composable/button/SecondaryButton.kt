package com.baghdad.tudee.ui.composable.button

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.designSystem.theme.TudeeTheme
import com.baghdad.tudee.ui.composable.StripedCircularProgressIndicator

@Composable
fun SecondaryButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isEnabled: Boolean = true,
) {
    val transition = updateTransition(
        targetState = isEnabled,
        label = "secondary_button_state"
    )

    val animatedBorderColor by transition.animateColor(
        label = "secondary_button_border_color",
        transitionSpec = { ButtonDefaults.defaultColorAnimationSpec }
    ) { enabled ->
        if (enabled) Theme.color.textColor.stroke
        else Theme.color.textColor.disable
    }

    val animatedContentColor by transition.animateColor(
        label = "secondary_button_content_color",
        transitionSpec = { ButtonDefaults.defaultColorAnimationSpec }
    ) { enabled ->
        if (enabled) Theme.color.primaryColor.normal
        else Theme.color.textColor.stroke
    }
    BasicButton(
        onClick = onClick,
        modifier = modifier.height(ButtonDefaults.defaultHeight),
        isEnabled = isEnabled,
        contentPadding = PaddingValues(vertical = 18.dp, horizontal = 24.dp),
        colors = ButtonColors(
            backgroundColor = Color.Transparent,
            contentColor = animatedContentColor
        ),
        border = BorderStroke(width = 1.dp, color = animatedBorderColor),
        shape = RoundedCornerShape(100.dp),
    ) {
        BasicText(
            text = label,
            style = Theme.typography.label.large.copy(
                color = animatedContentColor
            )
        )
        AnimatedVisibility(isLoading) {
            StripedCircularProgressIndicator(
                modifier = Modifier.padding(start = 8.dp),
                size = 18.dp,
                lineLength = 5.dp,
                lineWidth = 2.dp,
                color = animatedContentColor
            )
        }
    }
}

@Preview(showBackground = true,)
@Composable
private fun SecondaryButtonPreview1() {
    TudeeTheme {
        SecondaryButton(label = "Submit", onClick = {})
    }
}


@Preview(showBackground = true)
@Composable
private fun SecondaryButtonPreview2() {
    SecondaryButton("Submit", isLoading = true, onClick = {})
}

@Preview(showBackground = true)
@Composable
private fun SecondaryButtonPreview4() {
    SecondaryButton("Submit", isEnabled = false, onClick = {})
}