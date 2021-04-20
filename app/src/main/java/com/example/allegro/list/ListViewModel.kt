package com.example.allegro.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allegro.api.GithubRepository
import com.example.allegro.api.GithubService
import com.example.allegro.data.GithubSingleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    repository: GithubRepository
) : ViewModel() {

//    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)
//    val photos = currentQuery.switchMap { queryString ->
//        githubApi.searchRepositories().results.filter { it.name.startsWith(queryString) }
//    }

    val test = MutableLiveData(emptyList<GithubSingleRepository>())
    init {
        Log.d("ResponseListViewModel", "test")

        viewModelScope.launch {
            val res = repository.getRepositories()
            test.value = res.data
            Log.d("ResponseListViewModel", res.data?.take(5).toString())
        }
    }

    companion object {
        private const val DEFAULT_QUERY = ""
        private const val CURRENT_QUERY = "current_query"
    }
}