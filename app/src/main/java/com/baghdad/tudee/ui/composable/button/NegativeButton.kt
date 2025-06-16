package com.baghdad.tudee.ui.composable.button

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.designSystem.theme.TudeeTheme
import com.baghdad.tudee.ui.composable.StripedCircularProgressIndicator

@Composable
fun NegativeButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isEnabled: Boolean = true,
) {
    val transition = updateTransition(
        targetState = isEnabled,
        label = "negative_button_state"
    )

    val animatedBackgroundColor by transition.animateColor(
        label = "negative_button_background_color",
        transitionSpec = { ButtonDefaults.defaultColorAnimationSpec }
    ) { enabled ->
        if (enabled) Theme.color.status.errorVariant
        else Theme.color.textColor.disable
    }

    val animatedContentColor by transition.animateColor(
        label = "negative_button_background_color",
        transitionSpec = { ButtonDefaults.defaultColorAnimationSpec }
    ) { enabled ->
        if (enabled) Theme.color.status.error
        else Theme.color.textColor.stroke
    }
    BasicButton(
        onClick = onClick,
        modifier = modifier.height(ButtonDefaults.defaultHeight),
        isEnabled = isEnabled,
        contentPadding = PaddingValues(vertical = 18.dp, horizontal = 24.dp),
        colors = ButtonDefaults.colors(
            backgroundColor = animatedBackgroundColor,
            contentColor = animatedContentColor,
        ),
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


@Preview(showBackground = true)
@Composable
private fun NegativeButtonPreview1() {
    TudeeTheme {
        NegativeButton(label = "Submit", onClick = {})
    }
}


@Preview(showBackground = true)
@Composable
private fun NegativeButtonPreview2() {
    NegativeButton("Submit", isLoading = true, onClick = {})
}


@Preview(showBackground = true)
@Composable
private fun NegativeButtonPreview4() {
    NegativeButton("Submit", isEnabled = false, onClick = {})
}