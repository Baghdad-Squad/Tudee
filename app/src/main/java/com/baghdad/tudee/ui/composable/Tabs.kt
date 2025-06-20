package com.baghdad.tudee.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.domain.entity.Task
import com.baghdad.tudee.ui.shared.Selectable

@Composable
fun Tabs(
    selectableTabs: List<Selectable<TabItem>>,
    onTabSelected: (TabItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .drawBottomBorder(
                borderColor = Theme.color.textColor.stroke
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        selectableTabs.forEach { selectableTab ->
            Tab(
                modifier = Modifier.weight(1f),
                selectableTab = selectableTab,
                onClick = { onTabSelected(selectableTab.value) }
            )
        }
    }
}

data class TabItem(
    val title: String,
    val badgeCount: Int?,
    val status: Task.State = Task.State.TODO
)


@Composable
private fun Tab(
    selectableTab: Selectable<TabItem>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val selectedTitleStyle = Theme.typography.label.medium.copy(
        color = Theme.color.textColor.title,
        textAlign = TextAlign.Center
    )
    val unSelectedTitleStyle = Theme.typography.label.small.copy(
        color = Theme.color.textColor.hint,
        textAlign = TextAlign.Center
    )
    val titleStyle by remember(selectableTab.isSelected) {
        derivedStateOf {
            if (selectableTab.isSelected) {
                selectedTitleStyle
            } else {
                unSelectedTitleStyle
            }
        }
    }
    AnimatedUnderlineWrapper(
        isSelected = selectableTab.isSelected,
        color = Theme.color.secondaryColor,
        modifier = modifier,
    ) { underlineModifier ->
        Row(
            modifier = Modifier
                .height(40.dp)
                .animateContentSize(tween(ANIMATION_DURATION))
                .clickable { onClick() }
                .then(underlineModifier),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            BasicText(
                text = selectableTab.value.title,
                style = titleStyle,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            selectableTab.value.badgeCount?.let { count ->
                AnimatedVisibility(
                    visible = selectableTab.isSelected && count >= 0,
                    enter = badgeEnterAnimation,
                    exit = badgeExitAnimation
                ) {
                    Badge(modifier = Modifier.padding(start = 4.dp), count = count)
                }
            }
        }
    }
}

@Composable
private fun Badge(
    count: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(28.dp)
            .background(
                color = Theme.color.surfaceColor.surface,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        BasicText(
            text = count.toString(),
            maxLines = 1,
            style = Theme.typography.label.medium.copy(
                color = Theme.color.textColor.body,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
private fun AnimatedUnderlineWrapper(
    isSelected: Boolean,
    color: Color,
    modifier: Modifier = Modifier,
    animationSpec: AnimationSpec<Float> = tween(ANIMATION_DURATION),
    content: @Composable (modifier: Modifier) -> Unit
) {
    val animatedScale by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0f,
        animationSpec = animationSpec,
        label = "underline_size_scale"
    )

    val underlinePath = remember { Path() }
    content(
        modifier
            .wrapContentWidth()
            .drawBehind {
                if (animatedScale > 0f) {
                    val underlineWidth = size.width * animatedScale
                    val underlineHeight = 4.dp.toPx() * animatedScale
                    val startX = (size.width - underlineWidth) / 2f
                    val startY = size.height - underlineHeight

                    val rect = RoundRect(
                        rect = Rect(
                            offset = Offset(startX, startY),
                            size = Size(underlineWidth, underlineHeight)
                        ),
                        topLeft = CornerRadius(12.dp.toPx()),
                        topRight = CornerRadius(12.dp.toPx()),
                        bottomLeft = CornerRadius.Zero,
                        bottomRight = CornerRadius.Zero
                    )
                    underlinePath.reset()
                    underlinePath.addRoundRect(rect)
                    drawPath(
                        path = underlinePath,
                        color = color
                    )
                }
            }
    )
}


private fun Modifier.drawBottomBorder(
    borderColor: Color
) = this.drawBehind {
    drawRect(
        color = borderColor,
        topLeft = Offset(0f, size.height - 1.dp.toPx()),
        size = Size(size.width, 1.dp.toPx())
    )
}

private const val ANIMATION_DURATION = 200
private const val BADGE_INITIAL_SCALE = 0.1f

private val badgeEnterAnimation = scaleIn(
    animationSpec = tween(ANIMATION_DURATION),
    initialScale = BADGE_INITIAL_SCALE
) + expandHorizontally(animationSpec = tween(ANIMATION_DURATION))

private val badgeExitAnimation = scaleOut(
    animationSpec = tween(ANIMATION_DURATION),
    targetScale = BADGE_INITIAL_SCALE
) + shrinkHorizontally(tween(ANIMATION_DURATION))