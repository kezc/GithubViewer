package com.example.allegro.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.allegro.api.DefaultGithubDataRepository
import com.example.allegro.api.GithubService
import retrofit2.HttpException
import java.io.IOException

private const val GITHUB_STARTING_PAGE_INDEX = 1

class RepositoriesPagingSource(
    private val githubService: GithubService,
    private val sortingOption: GithubService.SortingOptions
) :
    PagingSource<Int, GithubRepository>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepository> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        return try {
            val repositories =
                githubService.searchRepositories(position, params.loadSize, sortingOption.value)
            LoadResult.Page(
                repositories,
                if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                if (repositories.isEmpty()) null else position + (params.loadSize / DefaultGithubDataRepository.NETWORK_PAGE_SIZE),
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GithubRepository>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}