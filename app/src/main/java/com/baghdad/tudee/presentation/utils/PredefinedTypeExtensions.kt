package com.baghdad.tudee.presentation.utils

import com.baghdad.tudee.R
import com.baghdad.tudee.domain.entity.Category


fun Category.PredefinedType.getLabelResId(): Int {
    return when (this) {
        Category.PredefinedType.GYM -> R.string.category_gym
        Category.PredefinedType.MEDICAL -> R.string.category_medical
        Category.PredefinedType.SHOPPING -> R.string.category_shopping
        Category.PredefinedType.FAMILY_AND_FRIEND -> R.string.category_family_and_friend
        Category.PredefinedType.COOKING -> R.string.category_cooking
        Category.PredefinedType.ENTERTAINMENT -> R.string.category_entertainment
        Category.PredefinedType.TRAVELING -> R.string.category_traveling
        Category.PredefinedType.AGRICULTURE -> R.string.category_agriculture
        Category.PredefinedType.CODING -> R.string.category_coding
        Category.PredefinedType.ADORATION -> R.string.category_adoration
        Category.PredefinedType.FIXING_BUGS -> R.string.category_fixing_bugs
        Category.PredefinedType.CLEANING -> R.string.category_cleaning
        Category.PredefinedType.WORK -> R.string.category_work
        Category.PredefinedType.BUDGETING -> R.string.category_budgeting
        Category.PredefinedType.SELF_CARE -> R.string.category_self_care
        Category.PredefinedType.EVENT -> R.string.category_event
        Category.PredefinedType.EDUCATION -> R.string.category_education
    }
}