package com.example.allegro.di

import android.content.Context
import com.example.allegro.api.GithubDataRepository
import com.example.allegro.api.GithubService
import com.example.allegro.db.RepositoriesDao
import com.example.allegro.db.RepositoriesDatabase
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun providesRepositoriesDatabase(@ApplicationContext appContext: Context): RepositoriesDatabase =
        RepositoriesDatabase.getDatabase(appContext)

    @Provides
    @Singleton
    fun providesRepositoriesDao(database: RepositoriesDatabase): RepositoriesDao = database.repositoriesDao()

    @Provides
    @Singleton
    fun providesGithubRepository(githubService: GithubService, dao: RepositoriesDao): GithubDataRepository =
        GithubDataRepository(githubService, dao)
}