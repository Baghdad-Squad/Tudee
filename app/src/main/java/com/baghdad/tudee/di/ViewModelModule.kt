package com.baghdad.tudee.di

import com.baghdad.tudee.ui.screens.categoryScreen.CategoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::CategoryViewModel)
}