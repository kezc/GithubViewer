package com.example.allegro.api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.allegro.data.GithubRepository
import com.example.allegro.data.RepositoriesPagingSource
import com.example.allegro.db.RepositoriesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubDataRepository @Inject constructor(
    private val githubService: GithubService,
    private val dao: RepositoriesDao
) {
    fun getRepositories(sortOption: GithubService.SortOptions) = Pager(
        PagingConfig(20, 40, false),
        pagingSourceFactory = { RepositoriesPagingSource(githubService, sortOption) }
    ).liveData

    private suspend fun saveRepositories(repositories: List<GithubRepository>) =
        withContext(Dispatchers.IO) {
            dao.insertRepositories(repositories)
        }
}