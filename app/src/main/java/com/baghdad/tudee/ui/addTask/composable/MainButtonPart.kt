package com.baghdad.tudee.ui.addTask.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.ui.composable.button.PrimaryButton
import com.baghdad.tudee.ui.composable.button.SecondaryButton

@Composable
fun MainButtonPart (
    titleText: String,
    paragraphText: String,
    dateTime: String
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .height(145.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val showButton by remember {
                derivedStateOf {
                    titleText.isNotBlank() && paragraphText.isNotBlank() && dateTime.isNotBlank()
                }
            }
            PrimaryButton(
                label = "add",
                onClick = {},
                isLoading = false,
                isEnabled = showButton,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
            SecondaryButton(
                label = "Cancel",
                onClick = {},
                isLoading = false,
                isEnabled = true,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
        }
    }
}