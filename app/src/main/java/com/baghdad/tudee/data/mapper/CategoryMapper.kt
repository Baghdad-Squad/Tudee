package com.baghdad.tudee.data.mapper

import com.baghdad.tudee.data.model.CategoryDto
import com.baghdad.tudee.domain.entity.Category
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
fun CategoryDto.toEntity(): Category {
    val image = when (imageType) {
        CategoryDto.IMAGE_TYPE_BYTE_ARRAY -> {
            Category.Image.ByteArray(imageBytes!!)
        }

        else -> throw IllegalArgumentException("Image type not supported: $imageType")
    }

    return Category(
        id = this.id,
        title = this.title,
        image = image
    )
}

@OptIn(ExperimentalUuidApi::class)
fun Category.toDto(): CategoryDto {
    return when (image) {
        is Category.Image.Predefined -> CategoryDto(
            id = this.id,
            title = this.title,
            imageType = CategoryDto.IMAGE_TYPE_PREDEFINED,
        )

        is Category.Image.ByteArray -> CategoryDto(
            id = this.id,
            title = this.title,
            imageType = CategoryDto.IMAGE_TYPE_BYTE_ARRAY,
        )
    }
}

fun List<CategoryDto>.toEntities(): List<Category> = this.map { it.toEntity() }

fun List<Category>.toDtos(): List<CategoryDto> = this.map { it.toDto() }