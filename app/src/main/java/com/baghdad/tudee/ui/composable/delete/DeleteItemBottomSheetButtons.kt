package com.baghdad.tudee.ui.composable.delete

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.ui.composable.button.NegativeButton
import com.baghdad.tudee.ui.composable.button.SecondaryButton

@Composable
fun DeleteItemBottomSheetButtons(
    deleteButtonText: String,
    cancelButtonText: String,
    onDeleteClick: () -> Unit,
    onCancelClick: () -> Unit,
    isLoading: Boolean = false

) {
    Column(
        Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NegativeButton(
            label = deleteButtonText,
            onClick = onDeleteClick,
            isLoading = isLoading,
            isEnabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(100.dp))
        )
        Spacer(modifier = Modifier.height(12.dp))

        SecondaryButton(
            label = cancelButtonText,
            onClick = onCancelClick,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(100.dp))
        )
    }

}