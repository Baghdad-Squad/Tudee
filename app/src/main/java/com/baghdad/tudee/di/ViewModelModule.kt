package com.baghdad.tudee.di

import com.baghdad.tudee.ui.screens.categoryTasksScreen.CategoryTasksViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {  CategoryTasksViewModel(get()) }
}