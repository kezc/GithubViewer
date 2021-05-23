package com.example.githubViewer.api

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.githubViewer.data.GithubContributor
import com.example.githubViewer.data.GithubRepository
import com.example.githubViewer.util.Resource

interface GithubDataRepository {
    fun getRepositories(user: String, sortingOption: GithubService.SortingOptions): LiveData<PagingData<GithubRepository>>
    fun getContributors(user: String, repositoryName: String): LiveData<Resource<List<GithubContributor>>>
}