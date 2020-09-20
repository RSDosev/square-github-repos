package com.rsdosev.domain.usecase

import com.rsdosev.domain.model.GitHubRepo
import kotlinx.coroutines.flow.Flow
import com.rsdosev.domain.model.Result

interface GetSquareReposUseCase {
    /**
     * Gets the Square GitHub repositories
     *
     * @return the result of getting repos list
     * */
    suspend fun getSquareRepos(): Flow<Result<List<GitHubRepo>>>
}