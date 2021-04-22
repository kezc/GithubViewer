package com.example.allegro.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.paging.PagingData
import com.example.allegro.api.GithubDataRepository
import com.example.allegro.api.GithubService
import com.example.allegro.data.GithubContributor
import com.example.allegro.data.GithubRepository
import com.example.allegro.util.Resource

class FakeGithubDataRepository : GithubDataRepository {
    private val githubFactory = GithubFactory()
    var githubServiceData = listOf(
        githubFactory.createGithubContributor(),
        githubFactory.createGithubContributor(),
        githubFactory.createGithubContributor()
    )

    var shouldReturnError = false

    private val observablePagingRepositories = MutableLiveData<PagingData<GithubRepository>>()

    override fun getRepositories(sortOption: GithubService.SortOptions): LiveData<PagingData<GithubRepository>> {
        return observablePagingRepositories
    }

    override fun getContributors(repositoryName: String): LiveData<Resource<List<GithubContributor>>> =
        liveData {
            emit(Resource.Loading())
            val result = if (shouldReturnError) {
                Resource.Error("Could not load data")
            } else {
                Resource.Success(githubServiceData)
            }
            emit(result)
        }
}