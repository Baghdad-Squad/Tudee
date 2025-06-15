package com.baghdad.tudee.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baghdad.tudee.R

@Composable
fun TudeeHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .offset(8.dp,y=25.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = Color(0xFF4AC0F2),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Tudee avatar",
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Column {
            Text(
                text = "Tudee",
                color = Color.White,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                )
            )
            Text(
                text = "Your cute Helper for Every Task",
                color = Color.White.copy(alpha = 0.9f),
                style = TextStyle(
                    fontWeight = FontWeight.W500,
                    fontSize = 12.sp,
                )
            )
        }
    }
}

@Composable
fun CircularIconButton(
    onClick: () -> Unit,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    backgroundColor: Color = Color.White,
    iconTintColor: Color = Color.Black
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .background(color = backgroundColor, shape = CircleShape)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = iconTintColor
        )
    }
}


@Composable
fun TasksAppBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .offset(y=20.dp)
            .fillMaxWidth()
            .height(64.dp)
            .background(
                color = Color(0xFF4AC0F2),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularIconButton(
            onClick = { /* Handle back button click */ },
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier.size(40.dp),
            iconTintColor = Color.Black
        )
        Text(
            modifier = Modifier,
            text = "Tasks",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black
        )
    }
}

@Composable
fun CombinedScreen() {
    Column(
        modifier = Modifier

            .size(400.dp,400.dp)
            .background(
                color = Color(0xFF4AC0F2),
                shape = RoundedCornerShape(0.dp)
            )
            ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TudeeHeader(modifier = Modifier.padding(16.dp))
        TasksAppBar(modifier = Modifier.padding(horizontal = 16.dp))
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CombinedScreenPreview() {
    MaterialTheme {
        CombinedScreen()
    }
}