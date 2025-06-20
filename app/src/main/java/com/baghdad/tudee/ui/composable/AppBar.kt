//package com.baghdad.tudee.ui.composable
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.painter.Painter
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.baghdad.tudee.R
//import com.baghdad.tudee.designSystem.textStyle.tudeeTextStyle
//import com.baghdad.tudee.designSystem.theme.Theme
//import com.baghdad.tudee.ui.utils.insideBorder
//import com.baghdad.tudee.ui.utils.noRippleClickable
//
//@Composable
//fun TopTudeeBar(
//    title: String,
//    description: String,
//    modifier: Modifier = Modifier,
//    onChangeTheme: () -> Unit,
//) {
//    Row(
//        modifier = modifier
//            .fillMaxWidth()
//            .height(72.dp)
//            .background(Theme.color.primaryColor.normal)
//            .padding(horizontal = 16.dp),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        LogoAndTitle(title = title, description = description)
//        // replace with theme switch icon
//        Box(
//            modifier = Modifier
//                .size(36.dp)
//                .border(
//                    width = 1.dp,
//                    color = Theme.color.textColor.onPrimaryStroke,
//                    shape = RoundedCornerShape(8.dp)
//                )
//                .padding(6.dp)
//                //.insideBorder(1.dp, Theme.color.textColor.onPrimaryStroke, cornerRadius = 8.dp)
//                .background(Theme.color.primaryColor.variant, shape = RoundedCornerShape(8.dp))
//                .noRippleClickable(onClick = onChangeTheme)
//        ){
//
//        }
//
//
//
//    }
//
//}
//
//
//@Composable
//private fun LogoAndTitle(
//    title: String,
//    description: String,
//) {
//    Row(
//
//        horizontalArrangement = Arrangement.spacedBy(8.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Box(
//            modifier = Modifier
//                .size(48.dp)
//                .background(Color(0x66FFFFFF), shape = RoundedCornerShape(12.dp))
//                .insideBorder(1.dp, Color(0x66FFFFFF), cornerRadius = 12.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Image(
//                painter = painterResource(R.drawable.ic_logo_robort),
//                contentDescription = "Tudee Logo",
//                modifier = Modifier
//            )
//        }
//        Column {
//            Text(
//                title,
//                color = Theme.color.textColor.onPrimary,
//                style = Theme.typography.logo,
//            )
//            Text(
//                description,
//                color = Theme.color.textColor.onPrimaryCaption,
//                style = Theme.typography.label.small,
//            )
//        }
//    }
//}
//
//@Composable
//@Preview
//fun TopTudeeBarPreview() {
//    TopTudeeBar(
//        title = "Tudee",
//        description = "Your personal assistant",
//        onChangeTheme = {}
//    )
//}