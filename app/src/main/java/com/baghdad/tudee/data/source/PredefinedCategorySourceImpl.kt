package com.baghdad.tudee.data.source

import com.baghdad.tudee.domain.entity.Category

class PredefinedCategorySourceImpl: PredefinedCategorySource {
    override fun getPredefinedCategories(): List<Category> {
        return Category.PredefinedType.entries.mapIndexed { index, type ->
            Category(
                id = -(index + 1L),
                title = type.formattedName,
                image = Category.Image.Predefined(type)
            )
        }
    }
}