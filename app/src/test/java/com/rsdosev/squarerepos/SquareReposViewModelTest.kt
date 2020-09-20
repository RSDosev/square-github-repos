package com.rsdosev.squarerepos

import androidx.lifecycle.Observer
import com.rsdosev.domain.model.GitHubRepo.Companion.dummyRepo1
import com.rsdosev.domain.model.GitHubRepo.Companion.dummyRepo2
import com.rsdosev.domain.usecase.GetSquareReposUseCase
import com.rsdosev.squarerepos.squarerepos.SquareReposViewModel
import com.rsdosev.squarerepos.utils.ViewState
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import com.rsdosev.domain.model.Result
import com.rsdosev.domain.model.Result.Success

class SquareReposViewModelTest : BaseViewModelTest() {

    private val getSquareReposUseCase = mockk<GetSquareReposUseCase>()
    private val observer = mockk<Observer<ViewState>>(relaxUnitFun = true)
    private val viewModel = SquareReposViewModel(getSquareReposUseCase)

    @Before
    override fun setup() {
        super.setup()
        viewModel.viewState.observeForever(observer)
    }

    @Test
    fun `given success, when getting square repos, then return ViewState Successful`() {

        //given
        val repos = listOf(dummyRepo1, dummyRepo2)
        coEvery { getSquareReposUseCase.getSquareRepos() } returns flowOf(Success(repos))

        //when
        viewModel.getSquareRepos()

        //then
        val slot = slot<ViewState>()

        verifyOrder {
            observer.onChanged(ViewState.Loading())
            observer.onChanged(capture(slot))
        }

        Assert.assertNotNull(slot.captured.data)

        (slot.captured.data)?.let {
            assertEquals(repos, it)
        }

        coVerify { viewModel.getSquareRepos() }
    }

    @Test
    fun `given error, when getting square repos, then return ViewState Error`() {

        //given
        val errorMsg = "errorMsg"
        coEvery { getSquareReposUseCase.getSquareRepos() } returns flowOf(
            Result.Error.BasicError(
                errorMsg = errorMsg
            )
        )

        //when
        viewModel.getSquareRepos()

        //then
        verifyOrder {
            observer.onChanged(ViewState.Loading())
            observer.onChanged(ViewState.Error(errorMsg))
        }
    }

    @Test
    fun `given network error, when getting square repos, then return ViewState NetworkError`() {

        //given
        coEvery { getSquareReposUseCase.getSquareRepos() } returns flowOf(Result.Error.NetworkError)

        //when
        viewModel.getSquareRepos()

        //then
        verifyOrder {
            observer.onChanged(ViewState.Loading())
            observer.onChanged(ViewState.NetworkError())
        }
    }
}