package com.baghdad.tudee.di

import com.baghdad.tudee.ui.screens.category.CategoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import com.baghdad.tudee.ui.main.MainViewModel
import com.baghdad.tudee.ui.screens.tasks.TasksViewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::TasksViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::CategoryViewModel)

}