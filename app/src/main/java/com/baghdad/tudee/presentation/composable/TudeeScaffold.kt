package com.baghdad.tudee.presentation.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TudeeScaffold(
    topBar: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    floatingActionButton: (@Composable () -> Unit)? = null,
    snackbar: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.matchParentSize()
        ) {
            topBar()
            content()
        }
        floatingActionButton?.let { fab ->
            Box(
                modifier = Modifier
                    .padding(end = 12.dp, bottom = 10.dp)
                    .align(Alignment.BottomEnd),
            ) {
                fab()
            }
        }
        snackbar?.let {  snackbarContent ->
            Box(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(16.dp)
                    .align(Alignment.TopCenter)
            ) {
                snackbarContent()
            }
        }
    }

}