package com.baghdad.tudee.di

import com.baghdad.tudee.viewModel.homescreenViewModel.HomeScreenViewModel
import com.baghdad.tudee.presentation.main.MainViewModel
import com.baghdad.tudee.presentation.screens.categories.CategoriesViewModel
import com.baghdad.tudee.presentation.screens.categoryTasksScreen.CategoryTasksViewModel
import com.baghdad.tudee.presentation.screens.tasks.TasksViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::TasksViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::TasksViewModel)
    viewModelOf(::CategoriesViewModel)

    viewModel { (categoryId: Long) ->
        CategoryTasksViewModel(categoryId, get(), get())
    }
}