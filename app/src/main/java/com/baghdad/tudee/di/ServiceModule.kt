package com.baghdad.tudee.di

import com.baghdad.tudee.data.service.CategoryServiceImpl
import com.baghdad.tudee.data.service.TaskServiceImpl
import com.baghdad.tudee.domain.service.CategoryService
import com.baghdad.tudee.domain.service.TaskService
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val serviceModule = module {
    singleOf(::CategoryServiceImpl) { bind<CategoryService>() }
    singleOf(::TaskServiceImpl) { bind<TaskService>() }
}