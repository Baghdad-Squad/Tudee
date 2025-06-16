package com.baghdad.tudee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.baghdad.tudee.designSystem.theme.TudeeTheme
import com.baghdad.tudee.ui.composable.dateYearDialog.DateDialog

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TudeeTheme {
                var showDatePicker by remember { mutableStateOf(false) }
                var selectedDateMillis by remember { mutableStateOf<Long?>(null) }
                DateDialog(
                    isShowDatePicker = showDatePicker,
                    onDismiss = { showDatePicker = false },
                    onDateSelected = { millis ->
                        selectedDateMillis = millis

                    })
            }
        }
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TudeeTheme {
        Greeting("Android")
    }
}