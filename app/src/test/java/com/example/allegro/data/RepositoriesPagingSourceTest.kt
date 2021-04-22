package com.example.allegro.data

import androidx.paging.PagingSource
import com.example.allegro.api.GithubService
import com.example.allegro.repository.FakeGithubService
import com.example.allegro.repository.GithubFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test


class RepositoriesPagingSourceTest {

    private val githubFactory = GithubFactory()
    var githubRepositories = listOf(
        githubFactory.createGithubRepository(),
        githubFactory.createGithubRepository(),
        githubFactory.createGithubRepository()
    )

    private val fakeGithubService =
        FakeGithubService().apply { githubRepositories.forEach { addGithubRepositories(it) } }

    @ExperimentalCoroutinesApi
    @Test
    fun `test first page`() = runBlockingTest {
        val pagingSource =
            RepositoriesPagingSource(fakeGithubService, GithubService.SortingOptions.FULL_NAME)
        val page = 1
        val perPage = 2
        assertThat(
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = page,
                    loadSize = perPage,
                    placeholdersEnabled = false
                )
            ),
            `is`(
                PagingSource.LoadResult.Page(
                    data = githubRepositories.drop((page - 1) * perPage)
                        .take(perPage),
                    nextKey = page + 1,
                    prevKey = null
                )
            )
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test middle page`() = runBlockingTest {
        val pagingSource =
            RepositoriesPagingSource(fakeGithubService, GithubService.SortingOptions.FULL_NAME)
        val page = 2
        val perPage = 2
        assertThat(
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = page,
                    loadSize = perPage,
                    placeholdersEnabled = false
                )
            ),
            `is`(
                PagingSource.LoadResult.Page(
                    data = githubRepositories.drop((page - 1) * perPage)
                        .take(perPage),
                    nextKey = page + 1,
                    prevKey = page - 1
                )
            )
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `test last page`() = runBlockingTest {
        val pagingSource =
            RepositoriesPagingSource(fakeGithubService, GithubService.SortingOptions.FULL_NAME)
        val page = 3
        val perPage = 2
        assertThat(
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = page,
                    loadSize = perPage,
                    placeholdersEnabled = false
                )
            ),
            `is`(
                PagingSource.LoadResult.Page(
                    data = emptyList(),
                    nextKey = null,
                    prevKey = page - 1
                )
            )
        )
    }
}