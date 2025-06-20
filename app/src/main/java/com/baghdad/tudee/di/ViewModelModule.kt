package com.baghdad.tudee.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import com.baghdad.tudee.ui.main.MainViewModel
import com.baghdad.tudee.ui.screens.tasks.TasksViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import com.baghdad.tudee.ui.screens.categoryTasksScreen.CategoryTasksViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::TasksViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::TasksViewModel)

    viewModel { (categoryId: Long) ->
        CategoryTasksViewModel(categoryId, get(), get())
    }
}