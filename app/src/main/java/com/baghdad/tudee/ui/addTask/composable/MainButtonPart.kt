package com.baghdad.tudee.ui.addTask.composable

import android.app.TaskInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.ui.composable.button.PrimaryButton
import com.baghdad.tudee.ui.composable.button.SecondaryButton

@Composable
fun MainButtonPart (isEnable: Boolean, initial: String? = null ){
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
            var value = "Add"
            if (initial != null) value = "Save" else value = "Add"
            PrimaryButton(
                label = value,
                onClick = {},
                isLoading = false,
                isEnabled = isEnable,
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