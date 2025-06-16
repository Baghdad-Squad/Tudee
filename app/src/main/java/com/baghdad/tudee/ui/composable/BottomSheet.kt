package com.baghdad.tudee.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.Theme


@Composable
fun BottomSheet(
    onDismiss:()->Unit,
    modifier: Modifier = Modifier,
    sheetHeight: Dp = 300.dp,
    content: @Composable ColumnScope.()->Unit,
    ) {
    Box{
        // scrim
        Box(modifier = Modifier.fillMaxSize()
            .background(Color(0x99000000))
            .clickable { onDismiss() })
        //Sheet
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(sheetHeight)
                .align(Alignment.BottomCenter)
        ){
            Surface(shape = RoundedCornerShape(24.dp),
                tonalElevation = 12.dp,
                color = Theme.color.surfaceColor.surface,
                modifier = modifier
                    .fillMaxSize()) {
                Column(modifier = Modifier
                    .padding(top = 16.dp)) {
                    Box(
                        modifier = Modifier
                            .width(32.dp)
                            .height(4.dp)
                            .align(Alignment.CenterHorizontally)
                            .background(color = Theme.color.textColor.body, shape = CircleShape)
                           // .padding(horizontal = 16.dp)
                    ){}
                    content()
                }
            }
        }
    }
}

@Composable
fun CustomBottomSheetDemoScreen() {
    var isSheetVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = { isSheetVisible = true }) {
            Text("BottomSheet")
        }

        if (isSheetVisible) {
            BottomSheet(onDismiss = { isSheetVisible = false }) {
                LazyColumn {
                    item(){
                        Text("text", style = MaterialTheme.typography.titleMedium)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { isSheetVisible = false }) {
                    Text("close")

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomSheetPreview(){
    CustomBottomSheetDemoScreen()

}