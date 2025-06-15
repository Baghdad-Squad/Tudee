package com.baghdad.tudee.data.service

import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.domain.service.CategoryService
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class CategoryServiceImpl(): CategoryService {
    override suspend fun getCategories(): Flow<List<Category>> {
        TODO("Not yet implemented")
    }

    override suspend fun createCategory(category: Category) {
        TODO("Not yet implemented")
    }

    override suspend fun editCategory(category: Category) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCategory(categoryId: UUID) {
        TODO("Not yet implemented")
    }
}