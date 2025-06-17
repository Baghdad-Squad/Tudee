package com.baghdad.tudee.di

import androidx.room.Room
import com.baghdad.tudee.data.database.TudeeDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single<TudeeDatabase> {
        Room.databaseBuilder(
            androidContext(),
            TudeeDatabase::class.java,
            TudeeDatabase.DATABASE_NAME
        ).build()
    }
    single { get<TudeeDatabase>().taskDao() }
    single { get<TudeeDatabase>().categoryDao() }
    single { get<TudeeDatabase>().appConfigurationDao() }
}