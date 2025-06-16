package com.baghdad.tudee.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.designSystem.theme.TudeeTheme

@Composable
fun TudeeCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(192.dp)
            .border(
                width = 1.dp,
                color = Theme.color.textColor.onPrimaryStroke,
                shape = RoundedCornerShape(32.dp)
            )
            .background(
                color = Theme.color.textColor.onPrimaryCard, shape = RoundedCornerShape(32.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 48.dp)
                .height(120.dp)
        ) {
            Text(
                text = title,
                style = Theme.typography.title.medium.copy(
                    color = Theme.color.textColor.title,
                ),
                modifier = Modifier.align(Alignment.TopCenter)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = description,
                style = Theme.typography.body.medium.copy(
                    color = Theme.color.textColor.body,
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Preview
@Composable
fun TudeeCardPreview() {
    TudeeTheme {
        TudeeCard(
            title = "Overwhelmed with tasks?",
            description = "Let’s bring some order to the chaos. Tudee is here to help you sort, plan, and breathe easier."
        )
    }
}