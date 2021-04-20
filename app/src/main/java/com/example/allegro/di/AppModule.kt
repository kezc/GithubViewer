package com.example.allegro.di

import com.example.allegro.api.GithubRepositoriesRemoteDataSource
import com.example.allegro.api.GithubRepository
import com.example.allegro.api.GithubService
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
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create()
//        val gson = GsonBuilder().create()
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
    fun providesGithubRepositoriesRemoteDataSource(githubService: GithubService): GithubRepositoriesRemoteDataSource =
        GithubRepositoriesRemoteDataSource(githubService)

    @Provides
    @Singleton
    fun providesGithubRepository(dataSource: GithubRepositoriesRemoteDataSource): GithubRepository =
        GithubRepository(dataSource)
}