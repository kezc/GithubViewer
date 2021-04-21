package com.example.allegro.api

import com.example.allegro.BuildConfig
import com.example.allegro.data.GithubContributor
import com.example.allegro.data.GithubRepository
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    companion object {
        const val BASE_URL = "https://api.github.com/"
        const val API_TOKEN = BuildConfig.GITHUB_TOKEN
    }

    @Headers("Authorization: token $API_TOKEN")
    @GET("orgs/allegro/repos")
    suspend fun searchRepositories(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("sort") sort: String,
    ): List<GithubRepository>

    @Headers("Authorization: token $API_TOKEN")
    @GET("repos/allegro/{repository_name}/contributors")
    suspend fun searchRepositoryContributors(
        @Path(value = "repository_name", encoded = true) repositoryName: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("sort") sort: String = SortOptions.PUSHED.value,
    ): Response<List<GithubContributor>>

    enum class SortOptions(val value: String) {
        CREATED("created"),
        UPDATED("updated"),
        PUSHED("pushed"),
        FULL_NAME("full_name");
    }
}