package com.rsdosev.data.repository

import com.rsdosev.data.source.remote.GitHubAPIService
import com.rsdosev.domain.model.Result
import com.rsdosev.data.source.remote.apiCall
import com.rsdosev.data.source.local.cache.MemoryCache
import com.rsdosev.domain.model.GitHubRepo
import com.rsdosev.domain.repository.SquareReposRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


const val SQUARE_REPOS_LIST_CACHE_KEY = "SQUARE_REPOS_LIST_CACHE_KEY"

class SquareReposRepositoryImpl(
    private val memoryCache: MemoryCache,
    private val apiService: GitHubAPIService
) : SquareReposRepository {

    /**
     * In-memory cache of the Square repos
     */
    private var cache: List<GitHubRepo>?
        get() = memoryCache.load<List<GitHubRepo>>(SQUARE_REPOS_LIST_CACHE_KEY)
        set(value) {
            if (value != null) memoryCache.save(SQUARE_REPOS_LIST_CACHE_KEY, value)
        }

    /**
     * Fetches the Square GitHub repositories from GitHub API, saved the result in in-memory cache and returns the result
     * Any subsequent method calls will first emit any cached data if present and then return API response if successful.
     * API call failures are returned only if the cache is empty.
     *
     * @return the result of getting repos list
     */
    override suspend fun getSquareRepos(): Flow<Result<List<GitHubRepo>>> = flow {
        cache?.let {
            emit(Result.Success(it))
        }

        val apiResult = apiCall { apiService.getSquareRepos() }

        if (apiResult is Result.Success) {
            cache = apiResult.data
            emit(
                Result.Success(
                    apiResult.data
                )
            )
        } else if (cache == null) {
            emit(apiResult)
        }
    }
}
