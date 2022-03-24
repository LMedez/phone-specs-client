package com.luc.domain.di

import com.luc.domain.usecases.GetUserUseCase
import com.luc.domain.usecases.LoginUseCase
import org.koin.dsl.module

val domainModule = module {
    single { LoginUseCase(get()) }
    single { GetUserUseCase(get()) }
}