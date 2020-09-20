package com.rsdosev.squarerepos.di

import com.rsdosev.domain.interactors.GetSquareReposInteractor
import com.rsdosev.domain.usecase.GetSquareReposUseCase
import com.rsdosev.domain.CoroutineContextProvider
import org.koin.dsl.module

val domainModule = module {
    single { CoroutineContextProvider() }

    factory<GetSquareReposUseCase> {
        GetSquareReposInteractor(
            get(),
            get()
        )
    }
}