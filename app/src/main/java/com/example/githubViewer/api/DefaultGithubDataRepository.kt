package com.example.githubViewer.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.githubViewer.data.GithubContributor
import com.example.githubViewer.util.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultGithubDataRepository @Inject constructor(
    private val githubService: GithubService
) : GithubDataRepository {
    override fun getRepositories(user: String, sortingOption: GithubService.SortingOptions) =
        Pager(
            PagingConfig(NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { RepositoriesPagingSource(githubService, user, sortingOption) }
        ).liveData

    override fun getContributors(
        user: String,
        repositoryName: String
    ): LiveData<Resource<List<GithubContributor>>> =
        liveData {
            emit(Resource.Loading<List<GithubContributor>>())
            try {
                val res = githubService.searchRepositoryContributors(
                    user = user,
                    repositoryName = repositoryName
                )
                val list = res.body()
                if (res.isSuccessful && list != null) {
                    emit(Resource.Success(list))
                } else {
                    emit(Resource.Error<List<GithubContributor>>("Could not get contributors"))
                }
            } catch (e: Exception) {
                emit(Resource.Error<List<GithubContributor>>(e.message ?: e.toString()))
            }
        }

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }
}