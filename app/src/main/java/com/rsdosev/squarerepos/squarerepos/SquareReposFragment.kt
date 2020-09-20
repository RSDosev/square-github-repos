package com.rsdosev.squarerepos.squarerepos

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.rsdosev.domain.model.GitHubRepo
import com.rsdosev.squarerepos.R
import com.rsdosev.squarerepos.utils.DividerItemDecorator
import com.rsdosev.squarerepos.utils.ImageLoader
import com.rsdosev.squarerepos.utils.NoNetworkException
import com.rsdosev.squarerepos.utils.ViewState
import com.rsdosev.squarerepos.utils.ViewStateType.*
import jp.wasabeef.recyclerview.animators.LandingAnimator
import kotlinx.android.synthetic.main.fragment_square_repos.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Represents the screen of Square GitHub repositories. It handles loading, no network, empty and success states
 */
class SquareReposFragment : Fragment(R.layout.fragment_square_repos) {

    private val viewModel: SquareReposViewModel by viewModel()
    private val imageLoader: ImageLoader by inject()

    override fun onStart() {
        super.onStart()
        viewModel.getSquareRepos()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            renderViewState(it)
        })
    }

    private fun initViews() {
        gitHubReposView.apply {
            adapter = GitHubReposAdapter(
                imageLoader = imageLoader
            )
            itemAnimator = LandingAnimator()
            addItemDecoration(DividerItemDecorator(resources.getDrawable(R.drawable.grey_list_divider)));
        }

    }

    private fun renderViewState(viewState: ViewState?) {
        viewState ?: return

        when (viewState.type) {
            LOADING -> showLoading()
            COMPLETED -> {
                hideLoading()
                showSquareRepos(
                    (viewState.data as? List<GitHubRepo>?) ?: emptyList()
                )
            }
            ERROR -> {
                hideLoading()
                when (viewState.error) {
                    is NoNetworkException -> showNoConnectionAvailable()
                    else -> showError(viewState.error?.message)
                }
            }
        }
    }

    private fun showError(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun showNoConnectionAvailable() {
        Toast.makeText(requireContext(), getString(R.string.no_network_error_message), Toast.LENGTH_LONG).show()
    }

    private fun showSquareRepos(repos: List<GitHubRepo>) {
        if (repos.isEmpty()) {
            showEmptyState()
            return
        }
        (gitHubReposView.adapter as? GitHubReposAdapter)?.submitList(repos)
        gitHubReposView.smoothScrollToPosition(0)
    }

    private fun showEmptyState() {
        Toast.makeText(requireContext(), getString(R.string.empty_state_error_message), Toast.LENGTH_LONG).show()
    }

    private fun hideLoading() {
        loadingView.isVisible = false
    }

    private fun showLoading() {
        loadingView.isVisible = true
    }
}
