package com.baghdad.tudee.di

import androidx.room.Room
import com.baghdad.tudee.data.database.TudeeDatabase
import com.baghdad.tudee.data.database.callback.DatabaseInitializerCallback
import com.baghdad.tudee.data.database.dao.AppConfigurationDao
import com.baghdad.tudee.data.database.dao.CategoryDao
import com.baghdad.tudee.data.database.dao.TaskDao
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val databaseModule = module {
    single<TudeeDatabase> {
        Room.databaseBuilder(
            androidContext(),
            TudeeDatabase::class.java,
            TudeeDatabase.DATABASE_NAME
        )
            .addCallback(DatabaseInitializerCallback { get<CategoryDao>() })
            .build()
    }
    single<CategoryDao> { get<TudeeDatabase>().categoryDao() }
    single<AppConfigurationDao> { get<TudeeDatabase>().appConfigurationDao() }
    single<TaskDao> { get<TudeeDatabase>().taskDao() }
}