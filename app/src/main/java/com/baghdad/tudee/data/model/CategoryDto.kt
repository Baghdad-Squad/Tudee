package com.baghdad.tudee.data.model

import java.util.UUID

data class CategoryDto(
    val id: UUID,
    val categoryTitle: String,
    val taskCount: Int,
    val imageUri: String

)
