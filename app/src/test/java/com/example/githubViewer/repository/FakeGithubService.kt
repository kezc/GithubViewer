package com.example.githubViewer.repository

import com.example.githubViewer.api.GithubService
import com.example.githubViewer.data.GithubContributor
import com.example.githubViewer.data.GithubRepository
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query

class FakeGithubService : GithubService {

    private val githubRepositories = mutableListOf<GithubRepository>()
    private val githubContributors = mutableListOf<GithubContributor>()

    fun addGithubRepositories(vararg repositories: GithubRepository) {
        githubRepositories.addAll(repositories)
    }

    fun addGithubContributors(vararg contributor: GithubContributor) {
        githubContributors.addAll(contributor)
    }

    override suspend fun searchRepositories(
        user: String,
        page: Int,
        perPage: Int,
        sort: String,
    ): List<GithubRepository> {
        return githubRepositories.drop((page - 1) * perPage).take(perPage)
    }

    override suspend fun searchRepositoryContributors(
        user: String,
        repositoryName: String,
        page: Int,
        perPage: Int,
        sort: String,
    ): Response<List<GithubContributor>> {
        return Response.success(githubContributors.drop((page - 1) * perPage).take(perPage))
    }
}