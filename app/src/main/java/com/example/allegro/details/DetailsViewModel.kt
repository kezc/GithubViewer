package com.example.allegro.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.allegro.api.GithubDataRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


class DetailsViewModel @AssistedInject constructor(
    repository: GithubDataRepository,
    @Assisted repositoryName: String
) : ViewModel() {

    @dagger.assisted.AssistedFactory
    interface AssistedFactory  {
        fun create(repositoryName: String): DetailsViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: AssistedFactory,
            repositoryName: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(repositoryName) as T
            }
        }
    }

    val contributors = repository.getContributors(repositoryName)
//    fun getContributors(repositoryName: String) = repository.getContributors(repositoryName)

}