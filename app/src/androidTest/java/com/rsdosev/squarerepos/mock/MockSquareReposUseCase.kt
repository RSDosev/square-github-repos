package com.rsdosev.squarerepos.mock

import com.rsdosev.domain.model.GitHubRepo
import com.rsdosev.domain.model.Result
import com.rsdosev.domain.usecase.GetSquareReposUseCase
import kotlinx.coroutines.flow.flowOf

/**
 * Mocked GetSquareReposUseCase on which we can manipulate the returned Square repos on demand
 */
class MockGetSquareReposUseCase : GetSquareReposUseCase {
    override suspend fun getSquareRepos() = flowOf(squareRepos)

    companion object {
        var squareRepos: Result<List<GitHubRepo>> = Result.Success(emptyList())
    }
}