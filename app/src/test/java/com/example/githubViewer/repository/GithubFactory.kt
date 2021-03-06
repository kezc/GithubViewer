package com.example.githubViewer.repository

import com.example.githubViewer.data.GithubContributor
import com.example.githubViewer.data.GithubRepository
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class GithubFactory {
    private val repositoryCounter = AtomicInteger(0)
    fun createGithubRepository(): GithubRepository {
        val id = repositoryCounter.incrementAndGet().toLong()
        return GithubRepository(
            id,
            "name_$repositoryCounter",
            "desc_$id",
            10,
            15,
            20,
            "Polish",
            "",
            Date(),
            Date(),
            GithubRepository.Owner(1, "Wojtek", "")
        )
    }

    private val contributorCounter = AtomicInteger(0)
    fun createGithubContributor(): GithubContributor {
        val id = contributorCounter.incrementAndGet().toLong()
        return GithubContributor(
            id, "login_$id", "", 20
        )
    }
}