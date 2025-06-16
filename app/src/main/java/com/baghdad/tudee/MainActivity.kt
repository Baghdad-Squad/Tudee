package com.baghdad.tudee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.designSystem.theme.TudeeTheme
import com.baghdad.tudee.ui.composable.dateYearDialog.DateDialog
import java.time.Instant
import java.time.ZoneId

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            setContent {
                TudeeTheme {
                    var showDatePicker by remember { mutableStateOf(false) }
                    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            Button(onClick = { showDatePicker = true }) {
                                Text("Open Date Picker")
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = selectedDateMillis?.let {
                                    val date = Instant.ofEpochMilli(it)
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate()
                                    "${date.dayOfWeek}, ${date.month} ${date.dayOfMonth}"
                                } ?: "No date selected",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        DateDialog(
                            isShowDatePicker = showDatePicker,
                            onDismiss = { showDatePicker = false },
                            onDateSelected = { millis -> selectedDateMillis = millis },
                        )
                    }
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
}