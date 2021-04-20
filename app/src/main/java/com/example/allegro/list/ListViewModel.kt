package com.example.allegro.list

import androidx.lifecycle.ViewModel
import com.example.allegro.api.GithubDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    repository: GithubDataRepository
) : ViewModel() {

//    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)
//    val photos = currentQuery.switchMap { queryString ->
//        githubApi.searchRepositories().results.filter { it.name.startsWith(queryString) }
//    }

    val repositories = repository.getRepositories()

    companion object {
        private const val DEFAULT_QUERY = ""
        private const val CURRENT_QUERY = "current_query"
    }
}