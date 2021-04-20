package com.example.allegro.api

import com.example.allegro.BuildConfig
import com.example.allegro.data.GithubRepository
import retrofit2.http.GET
import retrofit2.http.Headers
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

    enum class SortOptions(val value: String) {
        CREATED("created"),
        UPDATED("updated"),
        PUSHED("pushed"),
        FULL_NAME("full_name");
    }
}