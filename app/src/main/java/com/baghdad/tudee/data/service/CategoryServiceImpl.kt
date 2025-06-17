package com.baghdad.tudee.data.service

import com.baghdad.tudee.data.database.dao.CategoryDao
import com.baghdad.tudee.data.mapper.toDto
import com.baghdad.tudee.data.mapper.toEntity
import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.domain.service.CategoryService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CategoryServiceImpl(
    private val categoryDao: CategoryDao
) : CategoryService, BaseService() {
    override suspend fun getCategories(): Flow<List<Category>> = executeWithErrorHandling {
        categoryDao.getCategories().map { categories ->
                categories.map { category ->
                    category.toEntity()
                }
            }
    }

    override suspend fun createCategory(category: Category) = executeWithErrorHandling {
        categoryDao.createCategory(category.toDto())
    }

    override suspend fun editCategory(category: Category) = executeWithErrorHandling {
        categoryDao.updateCategory(category.toDto())
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun deleteCategory(categoryId: Uuid) = executeWithErrorHandling {
        categoryDao.deleteCategory(id = categoryId)
    }
}