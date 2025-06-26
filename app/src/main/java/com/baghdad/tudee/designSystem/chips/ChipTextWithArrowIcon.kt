package com.baghdad.tudee.designSystem.chips

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.ui.utils.getLocalizedNumber
import com.baghdad.tudee.ui.utils.noRippleClickable

@Composable
fun ChipTextWithArrowIcon(
    numberOfItem: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    Row(
        modifier
            .background(
                Theme.color.surfaceColor.surfaceHigh,
                shape = RoundedCornerShape(100.dp)
            )
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .noRippleClickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = getLocalizedNumber(numberOfItem),
            style = Theme.typography.label.medium,
            color = Theme.color.textColor.body
        )
        Icon(
            painter = painterResource(R.drawable.ic_arrow),
            tint = Theme.color.textColor.body,
            modifier = Modifier.graphicsLayer {
                scaleX = if (isRtl) -1f else 1f
            },
            contentDescription = stringResource(R.string.arrow_icon),
        )

    }

}