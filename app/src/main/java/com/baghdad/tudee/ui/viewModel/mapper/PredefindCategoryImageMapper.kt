package com.baghdad.tudee.ui.viewModel.mapper

import com.baghdad.tudee.R
import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.domain.entity.Category.Image

fun Category.PredefinedType.toDrawable() : Int{
    when(this){
        Category.PredefinedType.EDUCATION -> return R.drawable.ic_baseball_bat
        Category.PredefinedType.SHOPPING -> return R.drawable.ic_baseball_bat
        Category.PredefinedType.MEDICAL -> return R.drawable.ic_baseball_bat
        Category.PredefinedType.GYM -> return R.drawable.ic_baseball_bat
        Category.PredefinedType.ENTERTAINMENT -> return R.drawable.ic_baseball_bat
        Category.PredefinedType.COOKING -> return R.drawable.ic_baseball_bat
        Category.PredefinedType.FAMILY_AND_FRIEND -> return R.drawable.ic_baseball_bat
        Category.PredefinedType.TRAVELING -> return R.drawable.ic_baseball_bat
        Category.PredefinedType.AGRICULTURE -> return R.drawable.ic_baseball_bat
        Category.PredefinedType.CODING -> return R.drawable.ic_baseball_bat
        Category.PredefinedType.ADORATION -> return R.drawable.ic_baseball_bat
        Category.PredefinedType.FIXING_BUGS -> return R.drawable.ic_baseball_bat
        Category.PredefinedType.CLEANING -> return R.drawable.ic_baseball_bat
        Category.PredefinedType.WORK -> return R.drawable.ic_baseball_bat
        Category.PredefinedType.BUDGETING -> return R.drawable.ic_baseball_bat
        Category.PredefinedType.SELF_CARE -> return R.drawable.ic_baseball_bat
        Category.PredefinedType.EVENT -> return R.drawable.ic_baseball_bat
        else -> return R.drawable.ic_baseball_bat
    }

}

fun Int.toPredefinedType() : Category.PredefinedType{
    when(this){
        R.drawable.ic_baseball_bat -> return Category.PredefinedType.EDUCATION
        R.drawable.ic_baseball_bat -> return Category.PredefinedType.SHOPPING
        R.drawable.ic_baseball_bat -> return Category.PredefinedType.MEDICAL
        R.drawable.ic_baseball_bat -> return Category.PredefinedType.GYM
        R.drawable.ic_baseball_bat -> return Category.PredefinedType.ENTERTAINMENT
        R.drawable.ic_baseball_bat -> return Category.PredefinedType.COOKING
        R.drawable.ic_baseball_bat -> return Category.PredefinedType.FAMILY_AND_FRIEND
        R.drawable.ic_baseball_bat -> return Category.PredefinedType.TRAVELING
        R.drawable.ic_baseball_bat -> return Category.PredefinedType.AGRICULTURE
        R.drawable.ic_baseball_bat -> return Category.PredefinedType.CODING
        R.drawable.ic_baseball_bat -> return Category.PredefinedType.ADORATION
        R.drawable.ic_baseball_bat -> return Category.PredefinedType.FIXING_BUGS
        R.drawable.ic_baseball_bat -> return Category.PredefinedType.CLEANING
        R.drawable.ic_baseball_bat -> return Category.PredefinedType.WORK
        R.drawable.ic_baseball_bat -> return Category.PredefinedType.BUDGETING
        R.drawable.ic_baseball_bat -> return Category.PredefinedType.SELF_CARE
        R.drawable.ic_baseball_bat -> return Category.PredefinedType.EVENT
        else -> return Category.PredefinedType.EDUCATION
    }
}
