package com.baghdad.tudee.domain.service

import com.baghdad.tudee.domain.entity.Category
import kotlinx.coroutines.flow.Flow

interface CategoryService {
    suspend fun getCategories(): Flow<List<Category>>
    suspend fun createCategory(category: Category): Long
    suspend fun editCategory(category: Category): Int
    suspend fun deleteCategory(categoryId: Long): Int
}