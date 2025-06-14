package com.baghdad.tudee.ui.composable

import androidx.annotation.DrawableRes
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme


@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    onItemSelected: (Int) -> Unit,
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
                icon = if (index == selectedIndex) item.whenSelected else item.whenNotSelected,
                isSelected = index == selectedIndex,
                onClick = { onItemSelected(index) }
            )
        }
    }
}

@Composable
fun NavItem(
    @DrawableRes icon: Int,
    isSelected: Boolean = false,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .background(
                if (isSelected) Theme.color.primaryColor.variant.copy(alpha = 0.12f)
                else Color.Transparent,
                shape = CircleShape
            )
            .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null){
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = if (isSelected) Theme.color.primaryColor.normal
            else Theme.color.textColor.hint,
            modifier = Modifier.size(24.dp)
        )
    }
}

private val drawableNavItems = listOf(
    DrawableNavItem(
        whenSelected = R.drawable.ic_blue_home,
        whenNotSelected = R.drawable.ic_black_home
    ),
    DrawableNavItem(
        whenSelected = R.drawable.ic_blue_note,
        whenNotSelected = R.drawable.ic_black_note
    ),
    DrawableNavItem(
        whenSelected = R.drawable.ic_blue_menu,
        whenNotSelected = R.drawable.ic_menu_circle
    )
)

private data class DrawableNavItem(
    @DrawableRes val whenSelected: Int,
    @DrawableRes val whenNotSelected: Int,
)

@Composable
@Preview
private fun BottomNavigationPreview() {
    BottomNavigation() {}
}