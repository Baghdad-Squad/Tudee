package com.baghdad.tudee.domain.service

import com.baghdad.tudee.domain.entity.Category
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface CategoryService {


    suspend fun getCategories(): Flow<List<Category>>
    suspend fun createCategory(category: Category)
    suspend fun editCategory(category: Category)
    suspend fun deleteCategory(categoryId: UUID)}