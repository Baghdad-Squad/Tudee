package com.baghdad.tudee.ui.screens.categoryTasksScreen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.ui.utils.noRippleClickable


@Composable
fun IconInBox(
    modifier: Modifier = Modifier,
    icon: Int,
    onIconClick: () -> Unit,
    tint: Color = Theme.color.textColor.body
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .border(
                width = 1.dp,
                shape = CircleShape,
                color = Theme.color.textColor.stroke
            )
            .noRippleClickable { onIconClick() }
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = stringResource(R.string.arrow_left),
            tint = tint,
            modifier = Modifier
                .padding(10.dp)
        )
    }

}
