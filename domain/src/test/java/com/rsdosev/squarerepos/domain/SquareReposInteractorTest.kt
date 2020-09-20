package com.rsdosev.squarerepos.domain

import com.rsdosev.squarerepos.TestCoroutineContextProvider
import com.rsdosev.domain.model.Result
import com.rsdosev.domain.model.Result.Success
import com.rsdosev.domain.repository.SquareReposRepository
import com.rsdosev.domain.interactors.GetSquareReposInteractor
import com.rsdosev.domain.model.GitHubRepo.Companion.dummyRepo1
import com.rsdosev.domain.model.GitHubRepo.Companion.dummyRepo2
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

internal class SquareReposInteractorTest {

    private val repository = mockk<SquareReposRepository>()
    private val interactor =
        GetSquareReposInteractor(
            repository,
            TestCoroutineContextProvider()
        )

    private val ERROR_MSG = "errorMsg"

    @Test
    fun `given success when getting square repos then return Result Success`() {
        runBlocking {
            // given
            val repos = listOf(dummyRepo1, dummyRepo2)
            coEvery { repository.getSquareRepos() } returns flowOf(Success(repos))

            // when
            val result = interactor.getSquareRepos().toList().first()

            // then
            coVerifyOrder {
                repository.getSquareRepos()
            }

            assertTrue(result is Success)
            assertTrue((result as? Success)?.data == repos)
        }
    }

    @Test
    fun `given error when getting square repos then return Result Error`() {
        runBlocking {
            // given
            coEvery { repository.getSquareRepos() } returns flowOf(
                Result.Error.BasicError(
                    errorMsg = ERROR_MSG
                )
            )

            // when
            val result = interactor.getSquareRepos().toList().first()

            // then
            coVerifyOrder {
                repository.getSquareRepos()
            }

            assertTrue(result is Result.Error)
            assertEquals(ERROR_MSG, (result as? Result.Error.BasicError)?.errorMsg)
        }
    }

    @Test
    fun `given network error when getting square repos then return Result NetworkError`() {
        runBlocking {
            // given
            coEvery { repository.getSquareRepos() } returns flowOf(Result.Error.NetworkError)

            // when
            val result = interactor.getSquareRepos().toList().first()

            // then
            coVerifyOrder {
                repository.getSquareRepos()
            }

            assertTrue(result is Result.Error.NetworkError)
        }
    }

    @Test
    fun `given other errors when getting square repos then return Result GenericError`() {
        runBlocking {
            // given
            coEvery { repository.getSquareRepos() } returns flowOf(Result.Error.ApiCallError())

            // when
            val result = interactor.getSquareRepos().toList().first()

            // then
            coVerifyOrder {
                repository.getSquareRepos()
            }

            assertTrue(result is Result.Error.ApiCallError)
        }
    }
}