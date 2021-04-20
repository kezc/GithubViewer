package com.example.allegro.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GithubRepository @Inject constructor(private val localDataSource: GithubRepositoriesRemoteDataSource) {
    suspend  fun getRepositories() = withContext(Dispatchers.IO) {
        localDataSource.getRepositories()
    }
}