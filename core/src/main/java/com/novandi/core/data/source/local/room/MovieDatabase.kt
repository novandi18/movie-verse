package com.novandi.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.novandi.core.data.source.local.entity.MovieEntity
import com.novandi.core.data.source.local.entity.MovieTrendingEntity
import com.novandi.core.data.source.local.entity.RemoteKeyEntity
import com.novandi.core.data.source.local.room.dao.MovieDao
import com.novandi.core.data.source.local.room.dao.MovieTrendingDao
import com.novandi.core.data.source.local.room.dao.RemoteKeyDao

@Database(
    entities = [MovieEntity::class, MovieTrendingEntity::class, RemoteKeyEntity::class],
    version = 3,
    exportSchema = false
)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieTrendingDao(): MovieTrendingDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}