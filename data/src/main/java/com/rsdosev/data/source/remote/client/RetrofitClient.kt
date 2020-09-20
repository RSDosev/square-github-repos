package com.rsdosev.data.source.remote.client

import com.rsdosev.data.source.remote.GitHubAPIService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient(private val baseUrl: String) {

    val retrofitClient: Retrofit
        get() = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

    val gitHubAPIService
        get() = retrofitClient.create(GitHubAPIService::class.java)

    val okHttpClient: OkHttpClient
        get() = OkHttpClient.Builder()
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()

}