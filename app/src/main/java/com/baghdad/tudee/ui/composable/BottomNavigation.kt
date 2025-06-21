package com.baghdad.tudee.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    val currentRoute = navController.currentBackStackEntryAsState()
        .value?.destination?.route
        ?.substringBefore("?")

    val isVisible = remember(currentRoute) {
        listOf(Route.OnboardingScreen, Route.CategoryTasksScreen(0L))
            .map { it::class.qualifiedName.toString() }
            .none {
                currentRoute == it
            }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        ),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        )
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(Theme.color.surfaceColor.surfaceHigh)
                .padding(start = 32.dp, end = 32.dp)
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BottomNavigationRoute.entries.forEach { item ->
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
}

@Composable
fun NavItem(
    icon: Painter,
    isSelected: Boolean = false,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(42.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isSelected) Theme.color.primaryColor.variant
                else Color.Transparent,
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
