package com.example.githubViewer.api

import com.example.githubViewer.BuildConfig
import com.example.githubViewer.data.GithubContributor
import com.example.githubViewer.data.GithubRepository
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    @Headers("Authorization: token $API_TOKEN")
    @GET("users/{user}/repos")
    suspend fun searchRepositories(
        @Path(value = "user", encoded = true) user: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("sort") sort: String,
    ): List<GithubRepository>

    @Headers("Authorization: token $API_TOKEN")
    @GET("repos/{user}/{repository_name}/contributors")
    suspend fun searchRepositoryContributors(
        @Path(value = "user", encoded = true) user: String,
        @Path(value = "repository_name", encoded = true) repositoryName: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("sort") sort: String = SortingOptions.PUSHED.value,
    ): Response<List<GithubContributor>>

    companion object {
        const val BASE_URL = "https://api.github.com/"
        const val API_TOKEN = BuildConfig.GITHUB_TOKEN // Set it in gradle.properties
    }

    enum class SortingOptions(val value: String) {
        CREATED("created"),
        UPDATED("updated"),
        PUSHED("pushed"),
        FULL_NAME("full_name");
    }
}