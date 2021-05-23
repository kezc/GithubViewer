package com.example.githubViewer.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.githubViewer.R
import com.example.githubViewer.data.GithubContributor
import com.example.githubViewer.databinding.ItemGithubContributorBinding

class GithubContributorsAdapter :
    ListAdapter<GithubContributor, GithubContributorsAdapter.ContributorsViewHolder>(
        contributorsComparator
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorsViewHolder {
        val binding =
            ItemGithubContributorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContributorsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContributorsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ContributorsViewHolder(private val binding: ItemGithubContributorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contributor: GithubContributor) {
            binding.apply {
                login.text = contributor.login
                contributions.text = binding.root.context.resources.getQuantityString(
                    R.plurals.contributions,
                    contributor.contributions,
                    contributor.contributions
                )
                Glide.with(avatar)
                    .load(contributor.avatarUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.github_avatar_placeholder)
                    .apply(RequestOptions.circleCropTransform())
                    .into(avatar)
            }
        }
    }

    companion object {
        val contributorsComparator = object : DiffUtil.ItemCallback<GithubContributor>() {
            override fun areItemsTheSame(
                oldItem: GithubContributor,
                newItem: GithubContributor
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: GithubContributor,
                newItem: GithubContributor
            ): Boolean = oldItem == newItem
        }
    }
}