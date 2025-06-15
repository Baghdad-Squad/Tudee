package com.baghdad.tudee.ui.composable.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme

@Composable
fun LoadingFloatingActionButton(
    modifier: Modifier = Modifier,
    icon: Int = R.drawable.ic_white_loading,
    color: Brush = Theme.color.primaryColor.gradient
    ,onKlick:()->Unit={}
    ,isLoading:Boolean=true
    , isDiabled :Boolean= false
) {
    BasicButton(
        borderRadius = 100.dp,
        onClick = onKlick, backgroundColor = color,
        modifier = modifier.size(64.dp)
        , isLoading = isLoading
        , isDiabled = isDiabled
    ) {
        Image(
            painter = painterResource(icon), contentDescription = null,
            colorFilter = ColorFilter.tint(
                Theme.color.textColor.onPrimary
            )
        )
    }
}
