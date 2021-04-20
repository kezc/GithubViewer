package com.example.allegro.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.allegro.data.GithubRepository
import com.example.allegro.databinding.ItemGithubRepositoryBinding

class GithubRepositoriesAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<GithubRepository, GithubRepositoriesAdapter.ViewHolder>(
        repositoriesComparator
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemGithubRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let { holder.bind(it) }
    }

    inner class ViewHolder(private val binding: ItemGithubRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val repository = getItem(position)
                    if (repository != null) {
                        listener.onItemClick(repository)
                    }
                }
            }
        }

        fun bind(repository: GithubRepository) {
            binding.apply {
                name.text = repository.name
                starsCount.text = repository.stargazers_count.toString()
            }
        }
    }

    fun interface OnItemClickListener {
        fun onItemClick(repository: GithubRepository)
    }

    companion object {
        val repositoriesComparator = object : DiffUtil.ItemCallback<GithubRepository>() {
            override fun areItemsTheSame(
                oldItem: GithubRepository,
                newItem: GithubRepository
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: GithubRepository,
                newItem: GithubRepository
            ): Boolean =
                oldItem == newItem
        }
    }
}
