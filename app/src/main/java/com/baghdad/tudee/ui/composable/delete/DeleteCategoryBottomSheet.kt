package com.baghdad.tudee.ui.composable.delete



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme
import com.baghdad.tudee.ui.composable.button.NegativeButton
import com.baghdad.tudee.ui.composable.button.SecondaryButton


@Composable
fun deleteCategory(
    onDeleteClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(378.dp)
            .background(
                color = Theme.color.surfaceColor.surfaceLow,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(top = 24.dp)
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "Delete category",
                style = Theme.typography.title.large,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Subtext
            Text(
                text = "Are you sure to continue?",
                style = Theme.typography.body.medium,
                color = Theme.color.textColor.body,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( start = 16.dp),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Robot image
            Image(
                painter = painterResource(R.drawable.sad_robot),
                contentDescription = "Robot",
                modifier = Modifier
                    .width(107.dp)
                    .height(108.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Button Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(148.dp)
                    .background(
                        color = Theme.color.surfaceColor.surfaceHigh,
                        shape = RoundedCornerShape(
                            bottomStart = 24.dp,
                            bottomEnd = 24.dp
                        )
                    )


                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 12.dp,
                        bottom = 12.dp
                    )
            ) {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Delete Button
                    NegativeButton(
                        label = "Delete",
                        onClick = onDeleteClick,
                        isLoading = false,
                        isEnabled = true,
                        modifier = Modifier
                            .width(328.dp)
                            .height(56.dp)
                            .clip(RoundedCornerShape(100.dp))
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Cancel Button
                    SecondaryButton(
                        label = "Cancel",
                        onClick = onCancelClick,
                        modifier = Modifier
                            .width(328.dp)
                            .height(56.dp)
                            .clip(RoundedCornerShape(100.dp))
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun deleteCategoryPreview() {
    deleteCategory(
        onDeleteClick = {},
        onCancelClick = {}
    )
}
