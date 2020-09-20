package com.rsdosev.squarerepos.di

import com.rsdosev.data.source.remote.BASE_SERVICE_PATH
import com.rsdosev.data.source.remote.GitHubAPIService
import com.rsdosev.data.source.remote.client.RetrofitClient
import com.rsdosev.squarerepos.utils.ImageLoader
import com.rsdosev.squarerepos.utils.ImageLoaderImpl
import org.koin.dsl.module

val applicationModule = module {
    single<GitHubAPIService> {
        RetrofitClient(BASE_SERVICE_PATH).gitHubAPIService
    }

    single<ImageLoader> { ImageLoaderImpl(get()) }
}
