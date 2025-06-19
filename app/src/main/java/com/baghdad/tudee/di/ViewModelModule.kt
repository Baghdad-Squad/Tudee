package com.baghdad.tudee.di

import com.baghdad.tudee.viewModel.homescreenViewModel.HomeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import com.baghdad.tudee.ui.screens.tasks.TasksViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{ HomeScreenViewModel(get(), get()) }
    viewModelOf(::TasksViewModel)

}