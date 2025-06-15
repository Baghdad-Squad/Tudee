package com.baghdad.tudee.ui.composable.button

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.designSystem.theme.TudeeTheme
import com.baghdad.tudee.ui.composable.StripedCircularProgressIndicator

@Composable
fun TextButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isEnabled: Boolean = true,
    isError: Boolean = false,
) {
    val buttonColors = when {
        isEnabled.not() -> ButtonColors(
            backgroundColor = Color.Transparent,
            contentColor = Theme.color.textColor.stroke
        )

        isError -> ButtonDefaults.colors(
            backgroundColor = Color.Transparent,
            contentColor = Theme.color.status.error
        )

        else -> ButtonColors(
            backgroundColor = Color.Transparent,
            contentColor = Theme.color.primaryColor.normal
        )
    }
    BasicButton(
        onClick = onClick,
        modifier = modifier.height(ButtonDefaults.defaultHeight),
        isEnabled = isEnabled,
        contentPadding = PaddingValues(vertical = 18.dp, horizontal = 24.dp),
        colors = buttonColors,
        shape = RoundedCornerShape(100.dp),
    ) {
        BasicText(
            text = label,
            style = Theme.typography.label.large.copy(
                color = buttonColors.contentColor
            )
        )
        AnimatedVisibility(isLoading) {
            StripedCircularProgressIndicator(
                modifier = Modifier.padding(start = 8.dp),
                size = 18.dp,
                lineLength = 5.dp,
                lineWidth = 2.dp,
                color = buttonColors.contentColor
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun TextButtonPreview1() {
    TudeeTheme {
        TextButton(label = "Submit", onClick = {})
    }
}


@Preview(showBackground = true)
@Composable
private fun TextButtonPreview2() {
    TextButton("Submit", isLoading = true, onClick = {})
}

@Preview(showBackground = true)
@Composable
private fun TextButtonPreview3() {
    TextButton("Submit", isError = true, onClick = {})
}

@Preview(showBackground = true)
@Composable
private fun TextButtonPreview4() {
    TextButton("Submit", isEnabled = false, onClick = {})
}