package com.example.allegro.api

import com.example.allegro.data.GithubRepository
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

    @GET("orgs/allegro/repos")
    suspend fun searchRepositories(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): List<GithubRepository>

}