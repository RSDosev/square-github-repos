package com.rsdosev.data.source.remote

import com.rsdosev.domain.model.GitHubRepo
import retrofit2.http.GET

const val BASE_SERVICE_PATH = "https://api.github.com"
const val ORGANISATION_PATH = "/orgs"

interface GitHubAPIService {

    @GET("$ORGANISATION_PATH/square/repos")
    suspend fun getSquareRepos(): List<GitHubRepo>
}
