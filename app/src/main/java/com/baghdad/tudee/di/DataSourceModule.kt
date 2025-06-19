package com.baghdad.tudee.di

import com.baghdad.tudee.data.source.PredefinedCategorySource
import com.baghdad.tudee.data.source.PredefinedCategorySourceImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataSourceModule = module {
    singleOf(::PredefinedCategorySourceImpl) { bind<PredefinedCategorySource>() }
}