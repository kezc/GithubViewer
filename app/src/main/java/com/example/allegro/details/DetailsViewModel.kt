package com.example.allegro.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.allegro.api.GithubDataRepository
import com.example.allegro.data.GithubRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


class DetailsViewModel @AssistedInject constructor(
    repository: GithubDataRepository,
    @Assisted repo: GithubRepository
) : ViewModel() {
    val contributors = repository.getContributors(repo.owner.login, repo.name)

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(repositoryName: GithubRepository): DetailsViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: AssistedFactory,
            repository: GithubRepository
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(repository) as T
            }
        }
    }

}