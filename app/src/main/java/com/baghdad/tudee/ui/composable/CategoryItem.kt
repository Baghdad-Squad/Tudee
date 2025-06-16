package com.baghdad.tudee.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme

@Composable
fun CategoryItem(
    label: String,
    icon: Painter,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    count: Int? = null,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .width(104.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                enabled = true
            ) {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box() {
            Box(
                modifier = Modifier
                    .size(78.dp)
                    .clip(RoundedCornerShape(88.dp))
                    .background(color = Theme.color.surfaceColor.surfaceHigh)
            ) {
                Image(
                    painter = icon,
                    contentDescription = label,
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.Center)
                )
            }

            this@Column.AnimatedVisibility(
                visible = isSelected,
                enter = fadeIn(tween(300)) + expandHorizontally(tween(300)),
                exit = fadeOut(tween(300)) + shrinkHorizontally(tween(300))
            ) {
                Image(
                    painter = painterResource(R.drawable.selected_icon),
                    contentDescription = "$label badge icon",
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.TopEnd)
                        .offset(x = (-2).dp, y = 2.dp)
                )
            }

            this@Column.AnimatedVisibility(
                visible = !isSelected && count != null,
                enter = fadeIn(tween(300)) + expandHorizontally(tween(300)),
                exit = fadeOut(tween(300)) + shrinkHorizontally(tween(300))
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 36.dp, height = 20.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(color = Theme.color.surfaceColor.surfaceLow)
                        .align(Alignment.TopEnd)
                ) {
                    BasicText(
                        text = if (count in 0..99) count.toString() else "+99",
                        modifier = Modifier.align(Alignment.Center),
                        style = Theme.typography.label.small.copy(
                            color = Theme.color.textColor.hint
                        )
                    )
                }
            }
        }

        BasicText(
            text = label,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            style = Theme.typography.label.small.copy(
                color = Theme.color.textColor.body,
                textAlign = TextAlign.Center
            )
        )
    }
}

