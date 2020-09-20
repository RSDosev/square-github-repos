package com.rsdosev.domain.repository

import com.rsdosev.domain.model.GitHubRepo
import com.rsdosev.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface SquareReposRepository {

    /**
     * Gets the Square GitHub repositories
     *
     * @return the result of getting repos list
     */
    suspend fun getSquareRepos(): Flow<Result<List<GitHubRepo>>>
}
