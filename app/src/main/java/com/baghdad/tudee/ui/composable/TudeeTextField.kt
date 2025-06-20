package com.baghdad.tudee.ui.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme

@Composable
fun TudeeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    leadingIcon: Painter? = null,
    height: Int = 56,
    maxLines: Int = 1,
) {
    var isFocused by remember { mutableStateOf(false) }

    val animatedBorderColor by animateColorAsState(
        targetValue = if (isFocused) Theme.color.primaryColor.normal
        else Theme.color.textColor.stroke,
        animationSpec = tween(300)
    )

    val animatedContentColor by animateColorAsState(
        targetValue = if (value.isNotBlank()) Theme.color.textColor.body
        else Theme.color.textColor.hint,
        animationSpec = tween(300)
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height.dp)
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
                .padding(start = 12.dp, top = 13.dp, bottom = 13.dp)
        ) {
            leadingIcon?.let { icon ->
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = animatedContentColor,
                    modifier = Modifier.size(24.dp)
                )
                VerticalSeparator()
            }

            Box(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
                    .then(
                       if (leadingIcon == null) Modifier.padding(top = 5.dp)
                       else Modifier.align(Alignment.CenterVertically)
                    )
            ) {
                if (value.isEmpty() && !isFocused) {
                    Text(
                        text = hint,
                        color = Theme.color.textColor.hint,
                        style = Theme.typography.body.medium
                    )
                }
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        },
                    maxLines = maxLines,
                    readOnly = readOnly,
                    textStyle = Theme.typography.body.medium.copy(animatedContentColor)
                )
            }
        }
    }
}

@Composable
fun VerticalSeparator(){
    Box(
        Modifier
            .background(Theme.color.textColor.stroke)
            .fillMaxHeight()
            .width(1.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun TudeeTextFieldPreview(modifier: Modifier = Modifier){
    var name by remember { mutableStateOf("") }
    TudeeTextField(
        value = name,
        onValueChange = { name = it },
        hint = "aziz",
        modifier = modifier,
        leadingIcon = painterResource(id = R.drawable.ic_use),
    )
}