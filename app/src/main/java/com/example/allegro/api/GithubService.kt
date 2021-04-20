package com.example.allegro.api

import com.example.allegro.data.GithubSingleRepository
import retrofit2.Response
import retrofit2.http.GET

interface GithubService {

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

    @GET("orgs/allegro/repos")
    suspend fun searchRepositories(): Response<List<GithubSingleRepository>>

}