package com.example.githubViewer.data

data class GithubContributor(
    val id: Long,
    val login: String,
    val avatarUrl: String,
    val contributions: Int,
)
