package com.baghdad.tudee.presentation.composable

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.baghdad.tudee.R

@Composable
fun AnimatedSnackbar(
    snackbarState: SnackbarState,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = snackbarState.isVisible,
        enter = slideInHorizontally(
            initialOffsetX = { fullWidth -> fullWidth },
            animationSpec = tween(1000)
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { fullWidth -> -fullWidth },
            animationSpec = tween(1000)
        )
    ) {
        SnakeBar(
            message = stringResource(snackbarState.messageRes),
            isVisible = snackbarState.isVisible,
            isSuccess = snackbarState.isSuccess
        )
    }
}

data class SnackbarState(
    @StringRes
    val messageRes: Int = R.string.empty_string,
    val isSuccess: Boolean = true,
    val isVisible: Boolean = false
)