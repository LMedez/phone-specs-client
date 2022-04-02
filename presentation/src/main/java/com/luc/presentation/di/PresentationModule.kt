package com.luc.presentation.di

import com.luc.presentation.viewmodel.HomeViewModel
import com.luc.presentation.viewmodel.LoginViewModel
import com.luc.presentation.viewmodel.PhonesViewModel
import com.luc.presentation.viewmodel.SplashScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module{
     viewModel { LoginViewModel(get()) }
     viewModel { PhonesViewModel(get()) }
     viewModel { HomeViewModel(get()) }
     viewModel { SplashScreenViewModel(get(), get()) }
}