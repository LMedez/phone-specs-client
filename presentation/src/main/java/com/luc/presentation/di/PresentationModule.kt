package com.luc.presentation.di

import com.luc.presentation.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module{
     viewModel { LoginViewModel(get()) }
     viewModel { PhonesViewModel(get()) }
     viewModel { HomeViewModel(get()) }
     viewModel { SplashScreenViewModel(get(), get()) }
     viewModel { SearchViewModel(get()) }
}