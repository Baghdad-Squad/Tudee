package com.baghdad.tudee.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import com.baghdad.tudee.R
import com.baghdad.tudee.domain.entity.Category

@Composable
fun getCategoryIconPainter(categoryImage: Category.Image): Painter {
    return when (val image = categoryImage) {
        is Category.Image.Predefined -> {
            val resId = when (image.type) {
                Category.PredefinedType.EDUCATION -> return painterResource(R.drawable.ic_book_open)
                Category.PredefinedType.SHOPPING -> return painterResource(R.drawable.ic_shopping_cart)
                Category.PredefinedType.MEDICAL -> return painterResource(R.drawable.ic_hospital_location)
                Category.PredefinedType.GYM -> return painterResource(R.drawable.ic_body_part_muscle)
                Category.PredefinedType.ENTERTAINMENT -> return painterResource(R.drawable.ic_baseball_bat)
                Category.PredefinedType.COOKING -> return painterResource(R.drawable.ic_chef)
                Category.PredefinedType.FAMILY_AND_FRIEND -> return painterResource(R.drawable.ic_user_multiple)
                Category.PredefinedType.TRAVELING -> return painterResource(R.drawable.ic_airplane)
                Category.PredefinedType.AGRICULTURE -> return painterResource(R.drawable.ic_green_plant)
                Category.PredefinedType.CODING -> return painterResource(R.drawable.ic_thinking_person)
                Category.PredefinedType.ADORATION -> return painterResource(R.drawable.ic_quran)
                Category.PredefinedType.FIXING_BUGS -> return painterResource(R.drawable.ic_bug)
                Category.PredefinedType.CLEANING -> return painterResource(R.drawable.ic_blush_brush)
                Category.PredefinedType.WORK -> return painterResource(R.drawable.ic_oranig_bag)
                Category.PredefinedType.BUDGETING -> return painterResource(R.drawable.ic_money_bag)
                Category.PredefinedType.SELF_CARE -> return painterResource(R.drawable.ic_in_love)
                Category.PredefinedType.EVENT -> return painterResource(R.drawable.ic_birthday_cake)
                else -> {
                    return painterResource(R.drawable.ic_birthday_cake)
                }
            }
            painterResource(id = resId)
        }

        is Category.Image.ByteArray -> {
            rememberAsyncImagePainter(
                model = image.data,
                placeholder = painterResource(R.drawable.calendar_favorite_01),
                error = painterResource(R.drawable.calendar_favorite_01)
            )
        }
    }
}