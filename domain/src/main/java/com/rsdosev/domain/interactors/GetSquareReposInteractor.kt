package com.rsdosev.domain.interactors

import com.rsdosev.domain.CoroutineContextProvider
import com.rsdosev.domain.repository.SquareReposRepository
import com.rsdosev.domain.usecase.GetSquareReposUseCase
import kotlinx.coroutines.flow.flowOn


class GetSquareReposInteractor(
    private val squareReposRepository: SquareReposRepository,
    private val coroutineContextProvider: CoroutineContextProvider
) : GetSquareReposUseCase {

    override suspend fun getSquareRepos() =
        squareReposRepository.getSquareRepos().flowOn(coroutineContextProvider.IO)
}