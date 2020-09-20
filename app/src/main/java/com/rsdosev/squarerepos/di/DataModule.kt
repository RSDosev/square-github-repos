package com.rsdosev.squarerepos.di

import com.rsdosev.domain.repository.SquareReposRepository
import com.rsdosev.data.repository.SquareReposRepositoryImpl
import com.rsdosev.data.source.local.cache.DEFAULT_CACHE_SIZE
import com.rsdosev.data.source.local.cache.MemoryCache
import org.koin.dsl.module

val dataModule = module {
    single { MemoryCache(DEFAULT_CACHE_SIZE) }
    factory<SquareReposRepository> { SquareReposRepositoryImpl(get(), get()) }
}