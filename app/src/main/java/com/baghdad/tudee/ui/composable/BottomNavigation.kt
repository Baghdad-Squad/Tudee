package com.baghdad.tudee.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.ui.navigation.BottomNavigationRoute
import com.baghdad.tudee.ui.navigation.Route
import com.baghdad.tudee.ui.utils.noRippleClickable


@Composable
fun BottomNavigation(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route?.substringBefore("?")
    if(currentRoute?.startsWith(Route.OnboardingScreen::class.qualifiedName.toString()) == false)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.surfaceColor.surfaceHigh),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BottomNavigationRoute.entries.forEach{item ->
            (if (currentRoute == item.route::class.qualifiedName)
                item.selectedIcon
            else
                item.unSelectedIcon)?.let {
                painterResource(it)
            }?.let {
                NavItem(
                    isSelected = currentRoute == item.route::class.qualifiedName,
                    onClick = {
                        navController.navigate(item.route)
                    },
                    icon = it
                )
            }
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
            .noRippleClickable{
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
