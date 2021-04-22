package com.example.allegro.details


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.allegro.MainCoroutineRule
import com.example.allegro.getOrAwaitValue
import com.example.allegro.repository.FakeGithubDataRepository
import com.example.allegro.util.Resource
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

    @Before
    fun setUp() {
        githubDataRepository = FakeGithubDataRepository()
    }

    @Test
    fun `when cant fetch should return error`() {
        githubDataRepository.shouldReturnError = true
        val viewModel = DetailsViewModel(githubDataRepository, "")
        // before getting data there should be loading
        assertThat(viewModel.contributors.getOrAwaitValue() is Resource.Loading, `is`(true))
        // actual result
        assertThat(viewModel.contributors.getOrAwaitValue() is Resource.Error, `is`(true))
    }

    @Test
    fun `when no error fetch should return success`() {
        val viewModel = DetailsViewModel(githubDataRepository, "")
        // before getting data there should be loading
        assertThat(viewModel.contributors.getOrAwaitValue() is Resource.Loading, `is`(true))
        // actual result
        assertThat(viewModel.contributors.getOrAwaitValue() is Resource.Success, `is`(true))
    }
}