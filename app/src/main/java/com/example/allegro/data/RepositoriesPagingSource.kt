package com.example.allegro.data

import androidx.paging.PagingSource
import com.example.allegro.api.GithubService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class RepositoriesPagingSource @Inject constructor(private val githubService: GithubService) :
    PagingSource<Int, GithubRepository>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepository> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        return try {
            val repositories = githubService.searchRepositories(position, params.loadSize)
            LoadResult.Page(
                repositories,
                if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                if (repositories.isEmpty()) null else position + 1,
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}