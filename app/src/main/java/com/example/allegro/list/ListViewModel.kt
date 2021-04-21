package com.example.allegro.list

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.allegro.api.GithubDataRepository
import com.example.allegro.api.GithubService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    repository: GithubDataRepository,
    state: SavedStateHandle,
) : ViewModel() {

    private val _currentQuery = state.getLiveData(CURRENT_QUERY_KEY, DEFAULT_QUERY)
    val currentQuery: LiveData<GithubService.SortOptions>
        get() = _currentQuery

    val repositories = currentQuery.switchMap { sortOption ->
        repository.getRepositories(sortOption).cachedIn(viewModelScope)
    }

    fun changeSortOrder(order: GithubService.SortOptions) {
        _currentQuery.value = order
    }

    companion object {
        private val DEFAULT_QUERY = GithubService.SortOptions.FULL_NAME
        private const val CURRENT_QUERY_KEY = "current_query"
    }
}