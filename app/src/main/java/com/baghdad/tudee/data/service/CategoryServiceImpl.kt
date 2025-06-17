package com.baghdad.tudee.data.service

import com.baghdad.tudee.data.fakeData.fakeCategoriesData
import com.baghdad.tudee.domain.entity.Category
import com.baghdad.tudee.domain.service.CategoryService
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CategoryServiceImpl(): CategoryService {
    override suspend fun getCategories(): Flow<List<Category>> {
       return fakeCategoriesData()
    }

    override suspend fun createCategory(category: Category) {
        TODO("Not yet implemented")
    }

    override suspend fun editCategory(category: Category) {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun deleteCategory(categoryId: Uuid) {
        TODO("Not yet implemented")
    }


}