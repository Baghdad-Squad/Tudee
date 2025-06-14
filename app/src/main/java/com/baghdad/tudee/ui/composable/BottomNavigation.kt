package com.baghdad.tudee.ui.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    onCLick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.surfaceColor.surfaceHigh),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NavItem(
            icon = R.drawable.ic_black_home,
            isSelected = false){
            onCLick()
        }

        NavItem(
            icon = R.drawable.ic_blue_note,
            isSelected = true){
            onCLick()
        }

        NavItem(
            icon = R.drawable.ic_menu_circle,
            isSelected = false){
            onCLick()
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
            .size(40.dp)
            .background(
                if (isSelected) Theme.color.primaryColor.variant else Color.Transparent,
                shape = CircleShape
            ).clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(icon),
            null,
            tint = if (isSelected) Theme.color.primaryColor.normal else Theme.color.textColor.hint
        )
    }
}

@Composable
@Preview
private fun BottomNavigationPreview(){
    BottomNavigation(){}
}