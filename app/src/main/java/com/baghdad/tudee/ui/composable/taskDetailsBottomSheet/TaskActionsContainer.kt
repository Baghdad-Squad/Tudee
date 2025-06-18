package com.baghdad.tudee.ui.composable.taskDetailsBottomSheet

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.baghdad.tudee.R
import com.baghdad.tudee.designSystem.theme.Theme

@Composable
fun TaskActionsContainer(
    buttonText:String
){
    Row(
        modifier = Modifier.fillMaxWidth()
            .height(56.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            4.dp
        )
    ) {
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Theme.color.textColor.stroke,
                    shape = RoundedCornerShape(100)
                )
                .clip(RoundedCornerShape(100))
            , contentAlignment = Alignment.Center

        ) {
            Icon(
                painter = painterResource(id = R.drawable.pencil_edit_01),
                contentDescription = "icon edit",
                tint = Theme.color.primaryColor.normal,
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 24.dp)

            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .border(
                    width = 1.dp,
                    color = Theme.color.textColor.stroke,
                    shape = RoundedCornerShape(100)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = buttonText,
                style = Theme.typography.label.large,
                color = Theme.color.primaryColor.normal,
                modifier = Modifier.padding(vertical = 18.5.dp, horizontal = 54.5.dp)

            )
        }

    }
}