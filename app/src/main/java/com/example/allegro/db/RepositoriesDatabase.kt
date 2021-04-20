package com.example.allegro.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.allegro.data.GithubRepository

@Database(entities = [GithubRepository::class], version = 1)
@TypeConverters(Converters::class)
abstract class RepositoriesDatabase : RoomDatabase() {
    abstract fun repositoriesDao(): RepositoriesDao

    companion object {
        @Volatile
        private var instance: RepositoriesDatabase? = null

        fun getDatabase(context: Context): RepositoriesDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.inMemoryDatabaseBuilder(appContext, RepositoriesDatabase::class.java)
                .fallbackToDestructiveMigration()
                .build()
    }
}