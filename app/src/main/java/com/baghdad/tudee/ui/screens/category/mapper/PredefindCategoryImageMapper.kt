package com.baghdad.tudee.ui.screens.category.mapper

import com.baghdad.tudee.R
import com.baghdad.tudee.domain.entity.Category

fun Category.PredefinedType.toDrawable(): Int {
    when (this) {
        Category.PredefinedType.EDUCATION -> return R.drawable.ic_book_open
        Category.PredefinedType.SHOPPING -> return R.drawable.ic_shopping_cart
        Category.PredefinedType.MEDICAL -> return R.drawable.ic_hospital_location
        Category.PredefinedType.GYM -> return R.drawable.ic_body_part_muscle
        Category.PredefinedType.ENTERTAINMENT -> return R.drawable.ic_baseball_bat
        Category.PredefinedType.COOKING -> return R.drawable.ic_chef
        Category.PredefinedType.FAMILY_AND_FRIEND -> return R.drawable.ic_user_multiple
        Category.PredefinedType.TRAVELING -> return R.drawable.ic_airplane
        Category.PredefinedType.AGRICULTURE -> return R.drawable.ic_green_plant
        Category.PredefinedType.CODING -> return R.drawable.ic_thinking_person
        Category.PredefinedType.ADORATION -> return R.drawable.ic_quran
        Category.PredefinedType.FIXING_BUGS -> return R.drawable.ic_bug
        Category.PredefinedType.CLEANING -> return R.drawable.ic_blush_brush
        Category.PredefinedType.WORK -> return R.drawable.ic_oranig_bag
        Category.PredefinedType.BUDGETING -> return R.drawable.ic_money_bag
        Category.PredefinedType.SELF_CARE -> return R.drawable.ic_in_love
        Category.PredefinedType.EVENT -> return R.drawable.ic_birthday_cake
        else -> return R.drawable.ic_add_image
    }

}
