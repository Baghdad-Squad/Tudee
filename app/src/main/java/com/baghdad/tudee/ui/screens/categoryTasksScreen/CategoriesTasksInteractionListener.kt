package com.baghdad.tudee.ui.screens.categoryTasksScreen

import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.domain.entity.Task

interface CategoriesTasksInteractionListener {
    fun onTabSelected(tab: Task.State)
    fun onCategoryTitleChanged(title: String)
    fun onDeleteCategory()
    fun onSaveCategoryChanges()
    fun onChangeImage(newImage: Category.Image)
    fun onToggleEditCategorySheetVisibility()
}