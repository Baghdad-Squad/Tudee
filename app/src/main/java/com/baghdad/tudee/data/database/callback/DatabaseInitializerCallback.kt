package com.baghdad.tudee.data.database.callback

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.baghdad.tudee.data.database.dao.CategoryDao
import com.baghdad.tudee.data.mapper.toDtos
import com.baghdad.tudee.domain.entity.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseInitializerCallback(
    private val categoryDao: CategoryDao
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            initializePredefinedCategories()
        }
    }

    private suspend fun initializePredefinedCategories() {
        Category.PredefinedType.entries
            .mapIndexed { index, type ->
                Category(
                    id = index.toLong(),
                    title = type.formattedName,
                    image = Category.Image.Predefined(type)
                )
            }
            .toDtos()
            .forEach { category ->
                categoryDao.createCategory(category)
            }
    }
}