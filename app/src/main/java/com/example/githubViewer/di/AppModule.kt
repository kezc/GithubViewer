package com.example.githubViewer.di

import com.example.githubViewer.api.DefaultGithubDataRepository
import com.example.githubViewer.api.GithubDataRepository
import com.example.githubViewer.api.GithubService
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val gson = GsonBuilder()
             .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
             .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
             .create()
        return Retrofit.Builder()
            .baseUrl(GithubService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun providesGithubService(retrofit: Retrofit): GithubService =
        retrofit.create(GithubService::class.java)

    @Provides
    @Singleton
    fun providesGithubRepository(githubService: GithubService): GithubDataRepository =
        DefaultGithubDataRepository(githubService)
}