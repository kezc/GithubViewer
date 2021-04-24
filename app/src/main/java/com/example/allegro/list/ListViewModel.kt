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
    // Saving sorting option allows to correctly return to fragment
    // after process death
    private val currentSortingOption =
        state.getLiveData(CURRENT_SORTING_OPTION_KEY, DEFAULT_QUERY)

    val repositories = currentSortingOption.switchMap { sortOption ->
        repository.getRepositories(sortOption).cachedIn(viewModelScope)
    }

    fun changeSortingOrder(order: GithubService.SortingOptions) {
        currentSortingOption.value = order
    }

    fun getSortingOrder(): GithubService.SortingOptions? {
        return currentSortingOption.value
    }

    companion object {
        private val DEFAULT_QUERY = GithubService.SortingOptions.FULL_NAME
        private const val CURRENT_SORTING_OPTION_KEY = "current_sorting"
    }
}