package com.example.allegro.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
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

    private val _currentSortingOption = state.getLiveData(CURRENT_QUERY_KEY, DEFAULT_QUERY)
    val currentSortingOption: GithubService.SortingOptions?
        get() = _currentSortingOption.value

    val repositories = _currentSortingOption.switchMap { sortOption ->
        repository.getRepositories(sortOption).cachedIn(viewModelScope)
    }

    fun changeSortingOrder(order: GithubService.SortingOptions) {
        _currentSortingOption.value = order
    }

    companion object {
        private val DEFAULT_QUERY = GithubService.SortingOptions.FULL_NAME
        private const val CURRENT_QUERY_KEY = "current_query"
    }
}