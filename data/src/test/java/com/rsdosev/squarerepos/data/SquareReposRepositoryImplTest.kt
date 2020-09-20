package com.rsdosev.squarerepos.data

import com.rsdosev.domain.model.Result
import com.rsdosev.domain.model.Result.Success
import com.rsdosev.data.repository.SquareReposRepositoryImpl
import com.rsdosev.data.source.local.cache.MemoryCache
import com.rsdosev.data.source.remote.GitHubAPIService
import com.rsdosev.domain.model.GitHubRepo.Companion.dummyRepo1
import com.rsdosev.domain.model.GitHubRepo.Companion.dummyRepo2
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

internal class SquareReposRepositoryImplTest {

    private val memoryCache = MemoryCache()
    private val service = mockk<GitHubAPIService>()
    private var repository = SquareReposRepositoryImpl(memoryCache, service)

    @Test
    fun `given success response, when getting square repos then return Result Success`() {
        runBlocking {

            //given
            val repos = listOf(dummyRepo1, dummyRepo2)
            coEvery { service.getSquareRepos() } returns repos

            // when
            val result = repository.getSquareRepos().toList().first()

            // then
            coVerifyOrder {
                service.getSquareRepos()
            }

            assertTrue(result is Success)
            assertEquals(repos, (result as Success).data)
        }
    }

    @Test
    fun `given api call fail, when getting square repos then return Result Error`() {
        runBlocking {
            // given
            coEvery { service.getSquareRepos() } throws IOException()

            // when
            val result = repository.getSquareRepos().toList().first()

            // then
            coVerifyOrder {
                service.getSquareRepos()
            }

            assertTrue(result is Result.Error.NetworkError)
        }
    }

    @Test
    fun `given success response, when getting square repos, then return Result Success, then trying again and return cached`() {
        runBlocking {
            // given
            val firstApiResponse = listOf(dummyRepo1, dummyRepo2)
            // when
            coEvery { service.getSquareRepos() } returns firstApiResponse
            // then
            val firstApiCallResult = repository.getSquareRepos().toList().first()

            // given
            val secondApiResponse = listOf(dummyRepo2, dummyRepo1)
            // when
            coEvery { service.getSquareRepos() } returns secondApiResponse
            // then
            val (cachedResult, secondApiCallResult) = (repository.getSquareRepos().toList())

            // then
            coVerify(exactly = 2) {
                service.getSquareRepos()
            }

            assertTrue(firstApiCallResult is Success)
            assertTrue((firstApiCallResult as? Success)?.data == firstApiResponse)
            assertTrue(cachedResult is Success)
            assertTrue((cachedResult as? Success)?.data == firstApiResponse)
            assertTrue(secondApiCallResult is Success)
            assertTrue((secondApiCallResult as? Success)?.data != firstApiCallResult)
        }
    }
}