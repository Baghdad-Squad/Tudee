package com.baghdad.tudee.presentation.screens.categories

interface CategoriesInteractionListener {
    fun onCategoryClicked(
        categoryId: Long?
    )

    fun onAddCategoryClicked()

    fun onDismissAddCategorySheet()

    fun onAddCategory()

    fun onUpdateCategoryTitle(
        newTitle: String
    )

    fun onUpdateCategoryImageByteArray(
        categoryImageByteArray: ByteArray
    )
}

