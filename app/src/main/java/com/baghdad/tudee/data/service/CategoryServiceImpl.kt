package com.baghdad.tudee.data.service

import com.baghdad.tudee.data.database.dao.CategoryDao
import com.baghdad.tudee.data.mapper.toDto
import com.baghdad.tudee.data.mapper.toEntities
import com.baghdad.tudee.data.model.CategoryDto
import com.baghdad.tudee.data.service.shared.DatabaseErrorHandler
import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.domain.service.CategoryService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryServiceImpl(
    private val categoryDao: CategoryDao,

) : CategoryService, DatabaseErrorHandler() {
    override suspend fun getCategories(): Flow<List<Category>> = executeWithErrorHandling {
        categoryDao.getCategories().map(List<CategoryDto>::toEntities)
    }

    override suspend fun createCategory(category: Category) = executeWithErrorHandling {
        categoryDao.createCategory(category.toDto())
    }

    override suspend fun editCategory(category: Category) = executeWithErrorHandling {
        categoryDao.updateCategory(category.toDto())
    }

    override suspend fun deleteCategory(categoryId: Long) = executeWithErrorHandling {
        categoryDao.deleteCategory(id = categoryId)
    }
}