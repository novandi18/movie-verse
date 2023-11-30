package com.novandi.movieverse.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.novandi.movieverse.data.source.local.entity.MovieEntity
import com.novandi.movieverse.data.source.local.room.dao.MovieDao

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}