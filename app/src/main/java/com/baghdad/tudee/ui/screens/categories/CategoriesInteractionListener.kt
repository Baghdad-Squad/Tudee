package com.baghdad.tudee.ui.screens.categories

interface CategoriesInteractionListener {
    fun onAddCategory()
    fun onUpdateCategoryTitle(
        newTitle: String
    )
    fun onUpdateCategoryImage(
        byteArray: ByteArray
    )
    fun onToggleAddCategorySheetVisibility()
    fun onCategoryClick(
        categoryId: Long
    )
}