package com.baghdad.tudee.presentation.composable.bottomSheet.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.presentation.composable.button.PrimaryButton
import com.baghdad.tudee.presentation.composable.button.SecondaryButton

@Composable
fun ConfirmationButtonContainer(
    isEnabled: Boolean,
    onActionClick: () -> Unit,
    onCancelClick: () -> Unit,
    actionLabel:String,
    isLoading:Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Theme.color.surfaceColor.surfaceHigh),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            PrimaryButton(
                label = actionLabel,
                onClick = onActionClick,
                isLoading = isLoading,
                isEnabled = isEnabled,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
            SecondaryButton(
                label = stringResource(R.string.cancel),
                onClick = onCancelClick,
                isLoading = false,
                isEnabled = true,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
        }
    }
}
