package com.baghdad.tudee.di

import androidx.room.Room
import com.baghdad.tudee.data.database.TudeeDatabase
import com.baghdad.tudee.data.database.callback.DatabaseInitializerCallback
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
            .addCallback(DatabaseInitializerCallback(get()))
            .build()
    }
    singleOf(TudeeDatabase::appConfigurationDao)
    singleOf(TudeeDatabase::categoryDao)
    singleOf(TudeeDatabase::taskDao)
}