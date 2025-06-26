package com.baghdad.tudee.ui.screens.categories.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme

@Composable
fun CategoriesTopAppBar() {
    Box(
        modifier = Modifier
            .background(Theme.color.surfaceColor.surfaceHigh)
            .fillMaxWidth()
            .padding(WindowInsets.statusBars.asPaddingValues())

    ) {
        Text(
            text = stringResource(R.string.categories),
            modifier = Modifier.align(Alignment.CenterStart)
                .padding(vertical = 20.dp, horizontal = 16.dp),
            style = Theme.typography.title.large,
            color = Theme.color.textColor.title
        )
    }

}