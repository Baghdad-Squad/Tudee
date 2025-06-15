package com.baghdad.tudee.ui.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme

@Composable
fun TudeeTextField(
    text: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    paint: Painter? = null,
    textFieldHeight: Int = 56,
    lineMax: Int = 1,
) {
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
            .height(textFieldHeight.dp)
            .border(
                1.dp, animatedBorderColor, RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.TopStart
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top =  13.dp, bottom = 13.dp)
        ) {
            if (paint != null) {
                Image(
                    painter = paint,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(animatedContentColor),
                    modifier = Modifier.size(24.dp)
                )
                Box(
                    Modifier
                        .background(Theme.color.textColor.stroke)
                        .fillMaxHeight()
                        .width(1.dp)
                )
            }

            Box(Modifier.fillMaxWidth()
                .padding(vertical= 7.dp)) {
                if (text.isEmpty() && !isFocused) {
                    BasicText(
                        text = label,
                        style = TextStyle(
                            color = Theme.color.textColor.hint,
                            fontSize = Theme.typography.body.small.fontSize,
                            fontWeight = Theme.typography.body.medium.fontWeight
                        )
                    )
                }
                BasicTextField(
                    value = text,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        },
                    maxLines = lineMax,
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
