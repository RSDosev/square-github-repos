package com.rsdosev.squarerepos

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rsdosev.domain.model.GitHubRepo.Companion.dummyRepo1
import com.rsdosev.domain.model.GitHubRepo.Companion.dummyRepo2
import com.rsdosev.domain.model.Result
import com.rsdosev.squarerepos.base.BaseUITest
import com.rsdosev.squarerepos.base.LaunchActivity
import com.rsdosev.squarerepos.base.assertToastDisplayed
import com.rsdosev.squarerepos.mock.MockGetSquareReposUseCase
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListItemCount
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListNotEmpty
import com.schibsted.spain.barista.rule.BaristaRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SquareReposFragmentTest: BaseUITest() {

    @get:Rule
    var activityRule = BaristaRule.create(LaunchActivity::class.java)

    @Test
    fun givenSuccessfulReposLoadAssertRecycleViewNotEmpty() {
        MockGetSquareReposUseCase.squareRepos = Result.Success(listOf(dummyRepo1, dummyRepo2))

        activityRule.launchActivity()
        assertListNotEmpty(R.id.gitHubReposView)
        assertListItemCount(R.id.gitHubReposView, 2)
    }

    @Test
    fun givenNoNetworkAssertRecycleViewEmptyAndToastErrorMessageDisplayed() {
        MockGetSquareReposUseCase.squareRepos = Result.Error.NetworkError

        activityRule.launchActivity()
        assertListItemCount(R.id.gitHubReposView, 0)

        assertToastDisplayed(activityRule.activityTestRule.activity, "No network connection!")
    }

    @Test
    fun givenNoReposAssertRecycleViewEmptyAndToastErrorMessageDisplayed() {
        MockGetSquareReposUseCase.squareRepos = Result.Success(emptyList())

        activityRule.launchActivity()
        assertListItemCount(R.id.gitHubReposView, 0)

        assertToastDisplayed(activityRule.activityTestRule.activity, "There are no repos to present!")
    }

    @Test
    fun givenAPIErrorAssertRecycleViewEmptyAndToastErrorMessageDisplayed() {
        MockGetSquareReposUseCase.squareRepos = Result.Error.ApiCallError(errorMsg = "Server error!")

        activityRule.launchActivity()
        assertListItemCount(R.id.gitHubReposView, 0)

        assertToastDisplayed(activityRule.activityTestRule.activity, "Server error!")
    }
}