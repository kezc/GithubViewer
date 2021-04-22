package com.example.allegro.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.allegro.api.GithubDataRepository
import com.example.allegro.api.GithubService
import com.example.allegro.data.GithubContributor
import com.example.allegro.data.GithubRepository
import com.example.allegro.data.RepositoriesPagingSource
import com.example.allegro.util.Resource

class FakeGithubDataRepository(private val githubService: GithubService) : GithubDataRepository {
    var shouldReturnError = false

    override fun getRepositories(sortingOption: GithubService.SortingOptions): LiveData<PagingData<GithubRepository>> =
        Pager(
            PagingConfig(20, 40, false),
            pagingSourceFactory = { RepositoriesPagingSource(githubService, sortingOption) }
        ).liveData

    override fun getContributors(repositoryName: String): LiveData<Resource<List<GithubContributor>>> =
        liveData {
            emit(Resource.Loading())
            val result = if (shouldReturnError) {
                Resource.Error("Could not load data")
            } else {
                Resource.Success(
                    githubService.searchRepositoryContributors(repositoryName).body()!!
                )
            }
            emit(result)
        }
}