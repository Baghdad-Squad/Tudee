package com.baghdad.tudee.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
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
import kotlin.collections.forEachIndexed

@Composable
fun TabsBar(
    tabs: List<TabItem>,
    selectedTabIndex: Int,
    onTabClicked: (index: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = Theme.color.textColor.stroke
    Row(
        modifier = modifier
            .fillMaxWidth()
            .drawBottomBorder(borderColor = borderColor),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                modifier = Modifier.weight(1f),
                tab = tab,
                isSelected = selectedTabIndex == index,
                onClick = { onTabClicked(index) }
            )
        }
    }
}

data class TabItem(
    val title: String,
    val badgeCount: Int? = null
)

@Composable
private fun Tab(
    tab: TabItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val secondaryColor = Theme.color.secondaryColor
    val selectedTitleStyle = Theme.typography.label.medium.copy(color = Theme.color.textColor.title)
    val unSelectedTitleStyle = Theme.typography.label.small.copy(color = Theme.color.textColor.hint)
    val titleStyle by remember(isSelected) {
        derivedStateOf {
            if (isSelected) {
                selectedTitleStyle
            } else {
                unSelectedTitleStyle
            }
        }
    }
    val underlinePath = remember { Path() }
    Row(
        modifier = modifier
            .height(40.dp)
            .animateContentSize(tween(200))
            .clickable { onClick() }
            .drawSelectedUnderline(
                isSelected = isSelected,
                color = secondaryColor,
                underlinePath = underlinePath
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        BasicText(
            text = tab.title,
            style = titleStyle.copy(textAlign = TextAlign.Center),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.weight(1f)
        )
        tab.badgeCount?.let { count ->
            AnimatedVisibility(
                visible = isSelected && count > 0,
                enter = badgeEnterAnimation,
                exit = badgeExitAnimation
            ) {
                Badge(modifier = Modifier.padding(start = 4.dp), count = count)
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

private fun Modifier.drawBottomBorder(
    borderColor: Color
) = this.drawBehind {
    drawRect(
        color = borderColor,
        topLeft = Offset(0f, size.height - 1.dp.toPx()),
        size = Size(size.width, 1.dp.toPx())
    )
}

private fun Modifier.drawSelectedUnderline(
    isSelected: Boolean,
    color: Color,
    underlinePath: Path
) = this.wrapContentWidth().drawBehind {
    if (isSelected) {
        val cornerRadius = 12.dp.toPx()
        val rect = RoundRect(
            rect = Rect(
                offset = Offset(0f, size.height - 4.dp.toPx()),
                size = Size(size.width, 4.dp.toPx())
            ),
            topLeft = CornerRadius(cornerRadius),
            topRight = CornerRadius(cornerRadius),
            bottomLeft = CornerRadius.Zero,
            bottomRight = CornerRadius.Zero
        )
        drawPath(
            path = underlinePath.apply { addRoundRect(rect) },
            color = color
        )
    }
}

private val badgeEnterAnimation = scaleIn(
    animationSpec = tween(200),
    initialScale = 0.1f
) + expandHorizontally(animationSpec = tween(200))

private val badgeExitAnimation = scaleOut(
    animationSpec = tween(200),
    targetScale = 0.1f
) + shrinkHorizontally(tween(200))