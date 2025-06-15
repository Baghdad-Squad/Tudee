package com.baghdad.tudee.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme


@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    selectedIcon: Int = 0,
    unSelectedIcon: (Int) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.surfaceColor.surfaceHigh),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        drawableNavItems.forEachIndexed { index, item ->
            NavItem(
                icon = if (index == selectedIcon) painterResource(item.selectedIcon) else painterResource(item.unSelectedIcon),
                isSelected = index == selectedIcon,
                onClick = { unSelectedIcon(index) }
            )
        }
    }
}

@Composable
fun NavItem(
    icon: Painter,
    isSelected: Boolean = false,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .background(
                if (isSelected) Theme.color.primaryColor.variant
                else Color.Transparent,
                shape = CircleShape
            )
            .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null){
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = if (isSelected) Theme.color.primaryColor.normal
            else Theme.color.textColor.hint,
            modifier = Modifier.size(24.dp)
        )
    }
}

private val drawableNavItems = listOf(
    DrawableNavItem(
        selectedIcon = R.drawable.ic_blue_home,
        unSelectedIcon = R.drawable.ic_black_home
    ),
    DrawableNavItem(
        selectedIcon = R.drawable.ic_blue_note,
        unSelectedIcon = R.drawable.ic_black_note
    ),
    DrawableNavItem(
        selectedIcon = R.drawable.ic_blue_menu,
        unSelectedIcon = R.drawable.ic_menu_circle
    )
)

private data class DrawableNavItem(
    val selectedIcon: Int,
    val unSelectedIcon: Int,
)

@Composable
@Preview
private fun BottomNavigationPreview() {
    BottomNavigation {}
}