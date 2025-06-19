package com.baghdad.tudee.data.source

import com.baghdad.tudee.domain.entity.Category

interface PredefinedCategorySource {
    fun getPredefinedCategories(): List<Category>
}