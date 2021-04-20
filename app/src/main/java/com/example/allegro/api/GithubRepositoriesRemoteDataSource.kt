package com.example.allegro.api

import javax.inject.Inject

class GithubRepositoriesRemoteDataSource
@Inject constructor(private val githubService: GithubService) : BaseDataSource() {
    suspend fun getRepositories() = getResult { githubService.searchRepositories() }
}