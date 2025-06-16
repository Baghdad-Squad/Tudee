package com.baghdad.tudee.data.mapper

import com.baghdad.tudee.data.model.CategoryDto
import com.baghdad.tudee.domain.entity.Category
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun CategoryDto.toEntity():Category = Category(
    id = Uuid.parse(this.id),
    title = this.title,
    imageUri = this.imageUri
)

@OptIn(ExperimentalUuidApi::class)
fun Category.toDto():CategoryDto = CategoryDto(
    id = this.id.toString(),
    imageUri = this.imageUri,
    title = this.title,
)