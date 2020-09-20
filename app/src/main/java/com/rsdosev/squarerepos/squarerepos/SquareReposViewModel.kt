package com.rsdosev.squarerepos.squarerepos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsdosev.domain.model.GitHubRepo
import com.rsdosev.domain.model.Result
import com.rsdosev.domain.usecase.GetSquareReposUseCase
import com.rsdosev.squarerepos.utils.ViewState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * SquareReposFragment's corresponding ViewModel, responsible for loading the repositories and
 * managing the task's lifecycle (via viewModelScope). It passes the result to LiveData object
 * which is easily manageable by the fragment because of its lifecycle awareness
 */
open class SquareReposViewModel constructor(
    private val getSquareReposUseCase: GetSquareReposUseCase
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    fun getSquareRepos() {
        _viewState.value = ViewState.Loading()

        viewModelScope.launch {
            getSquareReposUseCase.getSquareRepos().collect {
                handleResult(it)
            }
        }
    }

    private fun handleResult(result: Result<List<GitHubRepo>>) {
        _viewState.value = when (result) {
            is Result.Success -> ViewState.Completed(result.data)
            is Result.Error.BasicError -> ViewState.Error(result.errorMsg)
            Result.Error.NetworkError -> ViewState.NetworkError()
            is Result.Error.ApiCallError -> ViewState.Error(result.errorMsg)
        }
    }
}
