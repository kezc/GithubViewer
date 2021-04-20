package com.example.allegro.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "repository")
data class GithubRepository(
    @PrimaryKey
    val id: Long,
    val name: String,
    val description: String,
    val stargazers_count: Int,
    val language: String,
    val html_url: String,
    val created_at: Date,
    val updated_at: Date,
)
