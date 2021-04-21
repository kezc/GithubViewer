package com.example.allegro.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.allegro.data.GithubContributor
import com.example.allegro.data.RepositoriesPagingSource
import com.example.allegro.util.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubDataRepository @Inject constructor(
    private val githubService: GithubService
) {
    fun getRepositories(sortOption: GithubService.SortOptions) = Pager(
        PagingConfig(20, 40, false),
        pagingSourceFactory = { RepositoriesPagingSource(githubService, sortOption) }
    ).liveData

    fun getContributors(repositoryName: String): LiveData<Resource<List<GithubContributor>>> =
        liveData {
            emit(Resource.Loading<List<GithubContributor>>())
            try {
                val res = githubService.searchRepositoryContributors(repositoryName)
                val list = res.body()
                if (res.isSuccessful && list != null) {
                    emit(Resource.Success(list))
                } else {
                    emit(Resource.Error<List<GithubContributor>>("Could not get contributors"))
                }
            } catch (e: IOException) {
                emit(Resource.Error<List<GithubContributor>>(e.message ?: e.toString()))
            } catch (e: HttpException) {
                emit(Resource.Error<List<GithubContributor>>(e.message ?: e.toString()))
            }
        }
}