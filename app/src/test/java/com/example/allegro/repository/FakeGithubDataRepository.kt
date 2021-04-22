package com.example.allegro.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.example.allegro.api.GithubDataRepository
import com.example.allegro.api.GithubService
import com.example.allegro.data.GithubContributor
import com.example.allegro.data.GithubRepository
import com.example.allegro.util.Resource
import kotlin.properties.Delegates

class FakeGithubDataRepository : GithubDataRepository {
    private val githubFactory = GithubFactory()
    var githubServiceData = listOf(
        githubFactory.createGithubContributor(),
        githubFactory.createGithubContributor(),
        githubFactory.createGithubContributor()
    )

    var shouldReturnError by Delegates.observable(false) { _, old, new ->
        println("$old, $new")
    }

    private val observableContributors = MutableLiveData<Resource<List<GithubContributor>>>()
    private val observablePagingRepositories = MutableLiveData<PagingData<GithubRepository>>()

    override fun getRepositories(sortOption: GithubService.SortOptions): LiveData<PagingData<GithubRepository>> {
        return observablePagingRepositories
    }

    override fun getContributors(repositoryName: String): LiveData<Resource<List<GithubContributor>>> {
        if (shouldReturnError) {
            observableContributors.value = Resource.Error("Could not load data")
        } else {
            observableContributors.value = Resource.Success(githubServiceData)
        }
        return observableContributors
    }
}