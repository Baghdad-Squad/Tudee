package com.baghdad.tudee.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.designSystem.theme.TudeeTheme

@Composable
fun OnboardingBackground() {

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .background(color = Theme.color.surfaceColor.surface)
            .background(color = Theme.color.status.overlay)
    ) {
        LocalConfiguration.current.screenWidthDp
        Box(
            modifier = Modifier
                .height(500.dp)
                .width(70.dp)
                .offset(x = (220).dp, y = -(200).dp)
                .rotate(220f)
                .background(color = Color.Green)//brush = Theme.color.surfaceColor.ellipsisGradientColor)
        ){
//            Image(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .offset(x = -(20).dp, y = -(260).dp)
//                    .blur(5.dp),
//                painter = painterResource(R.drawable.vector_1),
//                contentDescription = null,
//            )
        }
        Image(
            modifier = Modifier
                .size(523.dp, 501.dp)
                .offset(
                    x = (0.25 * LocalConfiguration.current.screenWidthDp).dp,
                    y = -(0.03 * LocalConfiguration.current.screenHeightDp).dp
                )
                .blur(5.dp),
            painter = painterResource(R.drawable.vector_2),
            contentDescription = null,
        )
        Image(
            modifier = Modifier
                .size(353.dp, 355.dp)
                .offset(
                    x = (0.38 * LocalConfiguration.current.screenWidthDp).dp,
                    y = (0.26 * LocalConfiguration.current.screenHeightDp).dp
                )
                .blur(5.dp),
            painter = painterResource(R.drawable.vector_3),
            contentDescription = null,
        )
    }
}


@Preview
@Composable
fun OnboardingBackgroundPreview() {
    TudeeTheme {
        OnboardingBackground()
    }
}
