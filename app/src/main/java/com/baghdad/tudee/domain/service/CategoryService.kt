package com.baghdad.tudee.domain.service

import com.baghdad.tudee.domain.entity.Category
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface CategoryService {


    suspend fun getCategories(): Flow<List<Category>>
    suspend fun createCategory(category: Category)
    suspend fun editCategory(category: Category)
    @OptIn(ExperimentalUuidApi::class)
    suspend fun deleteCategory(categoryId: Uuid)}