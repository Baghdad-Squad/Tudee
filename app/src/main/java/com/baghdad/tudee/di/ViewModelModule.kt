package com.baghdad.tudee.di

import com.baghdad.tudee.ui.main.MainViewModel
import com.baghdad.tudee.ui.screens.category.CategoryViewModel
import com.baghdad.tudee.ui.screens.categoryTasksScreen.CategoryTasksViewModel
import com.baghdad.tudee.ui.screens.tasks.TasksViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::TasksViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::TasksViewModel)
    viewModelOf(::CategoryViewModel)

    viewModel { (categoryId: Long) ->
        CategoryTasksViewModel(categoryId, get(), get())
    }
}