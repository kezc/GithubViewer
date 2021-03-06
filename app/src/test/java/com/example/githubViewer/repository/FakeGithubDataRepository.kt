package com.example.githubViewer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.githubViewer.api.GithubDataRepository
import com.example.githubViewer.api.GithubService
import com.example.githubViewer.api.RepositoriesPagingSource
import com.example.githubViewer.data.GithubContributor
import com.example.githubViewer.data.GithubRepository
import com.example.githubViewer.util.Resource

class FakeGithubDataRepository(private val githubService: GithubService) : GithubDataRepository {
    var shouldReturnError = false

    override fun getRepositories(
        user: String,
        sortingOption: GithubService.SortingOptions
    ): LiveData<PagingData<GithubRepository>> =
        Pager(
            PagingConfig(20, 40, false),
            pagingSourceFactory = { RepositoriesPagingSource(githubService, user, sortingOption) }
        ).liveData

    override fun getContributors(
        user: String,
        repositoryName: String
    ): LiveData<Resource<List<GithubContributor>>> =
        liveData {
            emit(Resource.Loading())
            val result = if (shouldReturnError) {
                Resource.Error("Could not load data")
            } else {
                Resource.Success(
                    githubService.searchRepositoryContributors(
                        user = user,
                        repositoryName = repositoryName
                    ).body()!!
                )
            }
            emit(result)
        }
}