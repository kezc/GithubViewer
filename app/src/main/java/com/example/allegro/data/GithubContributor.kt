package com.example.allegro.data

import com.google.gson.annotations.SerializedName

data class GithubContributor(
    val id: Long,
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val contributions: Int,
)
