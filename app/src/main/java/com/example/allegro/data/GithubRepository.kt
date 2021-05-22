package com.example.allegro.data

import android.os.Parcelable
import com.example.allegro.util.DateParceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler
import java.util.*

@Parcelize
@TypeParceler<Date, DateParceler>
data class GithubRepository(
    val id: Long,
    val name: String,
    val description: String?,
    val stargazersCount: Int,
    val forksCount: Int,
    val watchersCount: Int,
    val language: String?,
    val htmlUrl: String,
    val createdAt: Date,
    val updatedAt: Date,
    val owner: Owner,
) : Parcelable {
    @Parcelize
    data class Owner(
        val id: Long,
        val login: String,
        val avatarUrl: String,
    ) : Parcelable
}
