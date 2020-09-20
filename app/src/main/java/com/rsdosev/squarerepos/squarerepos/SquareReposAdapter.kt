package com.rsdosev.squarerepos.squarerepos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rsdosev.domain.model.GitHubRepo
import com.rsdosev.squarerepos.R
import com.rsdosev.squarerepos.utils.ImageLoader
import kotlinx.android.synthetic.main.list_item_github_repo.view.*

class GitHubReposAdapter(
    private val imageLoader: ImageLoader
) : ListAdapter<GitHubRepo, GitHubRepoViewHolder>(GitHubRepoItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GitHubRepoViewHolder(
        itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_github_repo, parent, false
        ),
        imageLoader = imageLoader
    )

    override fun onBindViewHolder(holder: GitHubRepoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class GitHubRepoViewHolder(
    itemView: View,
    private val imageLoader: ImageLoader
) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: GitHubRepo?) {
        item?.run {
            imageLoader.loadCircled(item.owner.avatarUrl, itemView.ownerAvatarView)
            itemView.repoNameView.text = name
            itemView.repoDescriptionView.text = if (description.isNullOrEmpty()) "..." else description

        }
    }
}

class GitHubRepoItemDiffCallback : DiffUtil.ItemCallback<GitHubRepo>() {

    override fun areItemsTheSame(
        oldItem: GitHubRepo,
        newItem: GitHubRepo
    ) = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: GitHubRepo,
        newItem: GitHubRepo
    ) = oldItem == newItem
}
