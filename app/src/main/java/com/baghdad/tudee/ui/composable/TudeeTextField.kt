package com.baghdad.tudee.ui.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme

@Preview(showBackground = true)
@Composable
fun TudeeTextField(
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }
    var hasBeenTyped by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(text) {
        if (text.isNotBlank()) {
            hasBeenTyped = true
        }
    }

    val animatedBorderColor by animateColorAsState(
        targetValue = if (isFocused) Theme.color.primaryColor.normal else Theme.color.textColor.stroke,
        animationSpec = tween(300)
    )

    val animatedContentColor by animateColorAsState(
        targetValue = if (hasBeenTyped) Theme.color.textColor.body else Theme.color.textColor.hint,
        animationSpec = tween(300)
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(
                1.dp, animatedBorderColor, RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_use),
                contentDescription = null,
                colorFilter = ColorFilter.tint(animatedContentColor),
                modifier = Modifier.size(24.dp)
            )

            Box(
                Modifier
                    .background(Theme.color.textColor.stroke)
                    .height(30.dp)
                    .width(1.dp)
            )

            Box(Modifier.fillMaxWidth()) {
                if (text.isEmpty() && !isFocused) {
                    Text(
                        text = "Full name",
                        style = TextStyle(
                            color = Theme.color.textColor.hint,
                            fontSize = Theme.typography.body.small.fontSize,
                            fontWeight = Theme.typography.body.medium.fontWeight
                        )
                    )
                }
                BasicTextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        },
                    singleLine = false,
                    textStyle = TextStyle(
                        color = animatedContentColor,
                        fontSize = Theme.typography.body.small.fontSize,
                        fontWeight = Theme.typography.body.medium.fontWeight
                    ),
                )
            }
        }

    }
}
