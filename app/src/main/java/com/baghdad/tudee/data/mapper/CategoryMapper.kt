package com.baghdad.tudee.data.mapper

import com.baghdad.tudee.data.model.CategoryDto
import com.baghdad.tudee.domain.entity.Category
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
fun CategoryDto.toEntity(): Category {
    val image = when (imageType) {
        CategoryDto.IMAGE_TYPE_BYTE_ARRAY -> {
            Category.Image.ByteArray(imageBytes)
        }

        CategoryDto.IMAGE_TYPE_PREDEFINED -> {
            Category.Image.Predefined(
                Category.PredefinedType.valueOf(imageData)
            )
        }

        else -> throw IllegalStateException("Image type not supported: $imageType")
    }

    return Category(
        id = this.id,
        title = this.title,
        image = image
    )
}


fun Category.toDto(): CategoryDto {
    return when (image) {
        is Category.Image.Predefined -> CategoryDto(
            id = this.id,
            title = this.title,
            imageType = CategoryDto.IMAGE_TYPE_PREDEFINED,
            imageData = this.image.type.name
        )

        is Category.Image.ByteArray -> CategoryDto(
            id = this.id,
            title = this.title,
            imageType = CategoryDto.IMAGE_TYPE_BYTE_ARRAY,
            imageBytes = this.image.data
        )

        Category.Image.Empty -> TODO()
    }
}

fun List<CategoryDto>.toEntities(): List<Category> = this.map { it.toEntity() }

fun List<Category>.toDtos(): List<CategoryDto> = this.map { it.toDto() }