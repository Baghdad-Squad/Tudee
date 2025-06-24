package com.baghdad.tudee.ui.composable.bottomSheet.category

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
