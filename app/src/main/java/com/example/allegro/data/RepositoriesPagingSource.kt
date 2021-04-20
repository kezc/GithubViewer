package com.example.allegro.data

import androidx.paging.PagingSource
import com.example.allegro.api.GithubService
import retrofit2.HttpException
import java.io.IOException

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class RepositoriesPagingSource(
    private val githubService: GithubService,
    private val sortOption: GithubService.SortOptions
) :
    PagingSource<Int, GithubRepository>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepository> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        return try {
            val repositories =
                githubService.searchRepositories(position, params.loadSize, sortOption.value)
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