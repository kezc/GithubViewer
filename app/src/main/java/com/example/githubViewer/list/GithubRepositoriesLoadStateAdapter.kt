package com.example.githubViewer.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubViewer.databinding.GithubRepositoriesLoadStateFooterBinding

class GithubRepositoriesLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<GithubRepositoriesLoadStateAdapter.LoadStateViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding =
            GithubRepositoriesLoadStateFooterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(private val binding: GithubRepositoriesLoadStateFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.retryButton.setOnClickListener {
                retry()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState !is LoadState.Loading
                errorMessage.isVisible = loadState !is LoadState.Loading
            }
        }
    }
}