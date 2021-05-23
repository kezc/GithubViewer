package com.example.githubViewer.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.githubViewer.MainCoroutineRule
import com.example.githubViewer.api.GithubService
import com.example.githubViewer.repository.FakeGithubDataRepository
import com.example.githubViewer.repository.FakeGithubService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ListViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var githubDataRepository: FakeGithubDataRepository

    @Before
    fun setUp() {
        githubDataRepository = FakeGithubDataRepository(FakeGithubService())
    }

    @Test
    fun `calling change sorting order should change sorting order`() {
        val viewModel = ListViewModel(githubDataRepository, SavedStateHandle())

        viewModel.changeSortingOrder(GithubService.SortingOptions.CREATED)
        assertThat(viewModel.getSortingOrder(), `is`(GithubService.SortingOptions.CREATED))

        viewModel.changeSortingOrder(GithubService.SortingOptions.FULL_NAME)
        assertThat(viewModel.getSortingOrder(), `is`(GithubService.SortingOptions.FULL_NAME))

        viewModel.changeSortingOrder(GithubService.SortingOptions.PUSHED)
        assertThat(viewModel.getSortingOrder(), `is`(GithubService.SortingOptions.PUSHED))

        viewModel.changeSortingOrder(GithubService.SortingOptions.UPDATED)
        assertThat(viewModel.getSortingOrder(), `is`(GithubService.SortingOptions.UPDATED))
    }
}