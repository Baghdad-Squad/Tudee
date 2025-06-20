package com.baghdad.tudee.di

import com.baghdad.tudee.viewModel.homescreenViewModel.HomeScreenViewModel
import com.baghdad.tudee.ui.main.MainViewModel
import com.baghdad.tudee.ui.screens.tasks.TasksViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::TasksViewModel)
    viewModelOf(::MainViewModel)

}