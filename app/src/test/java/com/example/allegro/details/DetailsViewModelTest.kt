package com.example.allegro.details


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.allegro.getOrAwaitValue
import com.example.allegro.repository.FakeGithubDataRepository
import com.example.allegro.util.Resource
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailsViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var githubDataRepository: FakeGithubDataRepository

    @Before
    fun setUp() {
        githubDataRepository = FakeGithubDataRepository()
    }

    @Test
    fun `when cant fetch should return error`() {
        githubDataRepository.shouldReturnError = true
        val viewModel = DetailsViewModel(githubDataRepository, "")
        val value = viewModel.contributors.getOrAwaitValue()
        assertThat(value is Resource.Error, `is`(true))
    }

    @Test
    fun `when no error fetch should return success`() {
        val viewModel = DetailsViewModel(githubDataRepository, "")
        val value = viewModel.contributors.getOrAwaitValue()
        assertThat(value is Resource.Success, `is`(true))
    }
}