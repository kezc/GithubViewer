package com.example.allegro.api

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.allegro.data.GithubContributor
import com.example.allegro.data.GithubRepository
import com.example.allegro.util.Resource

interface GithubDataRepository {
    fun getRepositories(sortingOption: GithubService.SortingOptions): LiveData<PagingData<GithubRepository>>
    fun getContributors(repositoryName: String): LiveData<Resource<List<GithubContributor>>>
}