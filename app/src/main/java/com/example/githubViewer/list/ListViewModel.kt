package com.example.githubViewer.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.githubViewer.api.GithubDataRepository
import com.example.githubViewer.api.GithubService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    repository: GithubDataRepository,
    state: SavedStateHandle,
) : ViewModel() {
    // Saving sorting option allows to correctly return to fragment
    // after process death
    private val currentSortingOption =
        state.getLiveData(CURRENT_SORTING_OPTION_KEY, DEFAULT_SEARCH_OPTION)

    private val currentQuery =
        state.getLiveData(CURRENT_QUERY_KEY, DEFAULT_QUERY)

    val repositories = currentSortingOption.switchMap { sortOption ->
        currentQuery.switchMap { query ->
            repository.getRepositories(query, sortOption).cachedIn(viewModelScope)
        }
    }

    fun changeSortingOrder(order: GithubService.SortingOptions) {
        currentSortingOption.value = order
    }

    fun getSortingOrder(): GithubService.SortingOptions? {
        return currentSortingOption.value
    }

    fun searchUser(query: String) {
        currentQuery.value = query
    }

    companion object {
        private val DEFAULT_SEARCH_OPTION = GithubService.SortingOptions.FULL_NAME
        private const val DEFAULT_QUERY = "akai-org"
        private const val CURRENT_SORTING_OPTION_KEY = "current_sorting"
        private const val CURRENT_QUERY_KEY = "current_query"
    }
}