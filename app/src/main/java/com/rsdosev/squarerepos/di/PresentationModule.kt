package com.rsdosev.squarerepos.di

import com.rsdosev.squarerepos.squarerepos.SquareReposViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { SquareReposViewModel(get()) }
}