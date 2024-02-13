package com.novandi.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.novandi.core.data.source.local.entity.MovieEntity
import com.novandi.core.data.source.local.entity.MoviePopularEntity
import com.novandi.core.data.source.local.entity.MovieTrendingEntity
import com.novandi.core.data.source.local.entity.RemoteKeyEntity
import com.novandi.core.data.source.local.entity.SearchEntity
import com.novandi.core.data.source.local.entity.UserEntity
import com.novandi.core.data.source.local.room.dao.MovieDao
import com.novandi.core.data.source.local.room.dao.MoviePopularDao
import com.novandi.core.data.source.local.room.dao.MovieTrendingDao
import com.novandi.core.data.source.local.room.dao.RemoteKeyDao
import com.novandi.core.data.source.local.room.dao.SearchDao
import com.novandi.core.data.source.local.room.dao.UserDao

@Database(
    entities = [
        MovieEntity::class,
        MoviePopularEntity::class,
        MovieTrendingEntity::class,
        RemoteKeyEntity::class,
        SearchEntity::class,
        UserEntity::class
    ],
    version = 6,
    exportSchema = false
)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun moviePopular(): MoviePopularDao
    abstract fun movieTrendingDao(): MovieTrendingDao
    abstract fun remoteKeyDao(): RemoteKeyDao
    abstract fun searchDao(): SearchDao
    abstract fun userDao(): UserDao
}