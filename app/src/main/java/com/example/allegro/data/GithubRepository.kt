package com.example.allegro.data

import android.os.Parcelable
import com.example.allegro.util.DateParceler
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler
import java.util.*

@Parcelize
@TypeParceler<Date, DateParceler>
data class GithubRepository(
    val id: Long,
    val name: String,
    val description: String?,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    @SerializedName("forks_count")
    val forksCount: Int,
    @SerializedName("watchers_count")
    val watchersCount: Int,
    val language: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("created_at")
    val createdAt: Date,
    @SerializedName("updated_at")
    val updatedAt: Date,
) : Parcelable
