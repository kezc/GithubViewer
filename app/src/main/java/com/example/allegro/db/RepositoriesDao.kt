package com.example.allegro.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.allegro.data.GithubRepository
import kotlinx.coroutines.flow.Flow

@Dao
interface RepositoriesDao {
    @Query("SELECT * FROM repository")
    fun getAllRepositories(): Flow<List<GithubRepository>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(repositories: List<GithubRepository>)

    @Query("SELECT * FROM repository WHERE id = :id")
    fun getRepository(id: Long): LiveData<GithubRepository>

    @Query("DELETE FROM repository")
    suspend fun deleteAllRepositories()
}