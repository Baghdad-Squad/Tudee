package com.baghdad.tudee.ui.composable.delete

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme

@Composable
fun DeleteItemBottomSheetHeader(
    title: String,
    message: String
) {
    Column(
        Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = title,
            style = Theme.typography.title.large,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = message,
            style = Theme.typography.body.medium,
            color = Theme.color.textColor.body,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(12.dp))

        Image(
            painter = painterResource(R.drawable.img_sad_robot),
            contentDescription = stringResource(R.string.sad_robot),
            modifier = Modifier
                .width(107.dp)
                .height(108.dp)
                .align(Alignment.CenterHorizontally)
        )

    }
}