package com.baghdad.tudee.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme

@Composable
fun CategoryItem(
    label: String,
    icon: Painter,
    modifier: Modifier = Modifier,
    count: Int = -1,
    isSelected: Boolean? = null,
) {
    Column(
        modifier = modifier
            .width(104.dp)
            .background(color = White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 13.dp)
                .padding(bottom = 25.61.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            when {
                isSelected == true && count < 0 -> {
                    Box(
                        modifier = Modifier
                            .size(width = 22.dp, height = 22.dp)
                            .padding(end = 2.dp, top = 2.dp)
                            .align(alignment = Alignment.End)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.selected_icon),
                            contentDescription = "$label badge icon",
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }
                }

                isSelected == false && count < 0 -> {
                    Spacer(Modifier.height(22.dp))
                }

                (isSelected == false || isSelected == null)
                        && count >= 0 -> {
                    val notificationCounter =
                        if (count in 0..99) count.toString() else "+99"

                    Box(
                        modifier = Modifier
                            .size(width = 36.dp, height = 20.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .background(color = Theme.color.surfaceColor.surfaceLow)
                            .align(alignment = Alignment.End)
                    ) {
                        Text(
                            text = notificationCounter,
                            style = Theme.typography.label.small,
                            color = Theme.color.textColor.hint,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))
                }
            }

            Spacer(modifier = Modifier.height(1.dp))

            Image(
                painter = icon,
                contentDescription = label,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        BasicText(
            text = label,
            modifier = Modifier
                .fillMaxWidth(),
            style = Theme.typography.label.small.copy(
                color = Theme.color.textColor.body,
                textAlign = TextAlign.Center
            )
        )
    }
}