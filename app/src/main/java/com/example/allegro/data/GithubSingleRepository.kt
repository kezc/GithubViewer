package com.example.allegro.data

import java.util.*

data class GithubSingleRepository(
    val name: String,
    val description: String,
    val stargazers_count: Int,
    val language: String,
    val html_url: String,
    val created_at: Date,
    val updated_at: Date,
)
