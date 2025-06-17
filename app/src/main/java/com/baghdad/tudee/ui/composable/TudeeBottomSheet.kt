package com.baghdad.tudee.ui.composable

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TudeeBottomSheet(
    onDismissRequest: () -> Unit,
    isVisible: Boolean, // state came from ViewModel
    content: @Composable ColumnScope.() -> Unit
) {

    val sheetState = rememberModalBottomSheetState()
    LaunchedEffect(isVisible) {
        if (isVisible) {
            sheetState.show()
        } else {
            sheetState.hide()
        }
    }

    if (sheetState.isVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            containerColor = Theme.color.surfaceColor.surface,
            scrimColor = Color(0x99000000),
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TudeeBottomSheetPreview() {
    // TudeeBottomSheet()
}