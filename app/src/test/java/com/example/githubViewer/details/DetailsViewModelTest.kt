package com.example.githubViewer.details


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.githubViewer.MainCoroutineRule
import com.example.githubViewer.getOrAwaitValue
import com.example.githubViewer.repository.FakeGithubDataRepository
import com.example.githubViewer.repository.FakeGithubService
import com.example.githubViewer.repository.GithubFactory
import com.example.githubViewer.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailsViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var githubDataRepository: FakeGithubDataRepository
    private val githubFactory = GithubFactory()
    private val githubContributors = listOf(
        githubFactory.createGithubContributor(),
        githubFactory.createGithubContributor(),
        githubFactory.createGithubContributor()
    )

    @Before
    fun setUp() {
        val fakeGithubService =
            FakeGithubService().apply { githubContributors.forEach { addGithubContributors(it) } }
        githubDataRepository = FakeGithubDataRepository(fakeGithubService)
    }

    @Test
    fun `when cant fetch, should return error`() {
        githubDataRepository.shouldReturnError = true
        val viewModel = DetailsViewModel(githubDataRepository, githubFactory.createGithubRepository())
        // before getting data there should be loading
        assertThat(viewModel.contributors.getOrAwaitValue() is Resource.Loading, `is`(true))
        // actual result
        assertThat(viewModel.contributors.getOrAwaitValue() is Resource.Error, `is`(true))
    }

    @Test
    fun `when successful fetch, should return success`() {
        val viewModel = DetailsViewModel(githubDataRepository, githubFactory.createGithubRepository())
        // before getting data there should be loading
        assertThat(viewModel.contributors.getOrAwaitValue() is Resource.Loading, `is`(true))
        // actual result
        assertThat(viewModel.contributors.getOrAwaitValue() is Resource.Success, `is`(true))
    }
}