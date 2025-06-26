package com.baghdad.tudee.presentation.composable.bottomSheet.category

import com.baghdad.tudee.domain.entity.Category

data class AddEditCategorySheetUiState(
    val isVisible: Boolean = false,
    val isEditing: Boolean = false,
    val isLoading: Boolean = false,
    val categoryTitle: String = "",
    val categoryImageByteArray: ByteArray? = null,
) {
    val isActionButtonEnabled
        get() = categoryTitle.isNotBlank() && isCategoryImageUploaded

    val isCategoryImageUploaded
        get() = categoryImageByteArray != null
}

fun AddEditCategorySheetUiState.toEntity(categoryId: Long? = null): Category = Category(
    id = categoryId ?: 0L,
    title = categoryTitle,
    image = Category.Image.ByteArray(
        this.categoryImageByteArray ?: byteArrayOf()
    )
)