package com.novandi.core.di

import android.content.Context
import androidx.room.Room
import com.novandi.core.data.source.local.room.MovieDatabase
import com.novandi.core.data.source.local.room.dao.MovieDao
import com.novandi.core.data.source.local.room.dao.MoviePopularDao
import com.novandi.core.data.source.local.room.dao.MovieTrendingDao
import com.novandi.core.data.source.local.room.dao.RemoteKeyDao
import com.novandi.core.data.source.local.room.dao.SearchDao
import com.novandi.core.utils.Consts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase =
        Room.databaseBuilder(
            context, MovieDatabase::class.java, Consts.DATABASE_NAME
        ).build()

    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao = database.movieDao()

    @Provides
    fun provideMovieTrendingDao(database: MovieDatabase): MovieTrendingDao =
        database.movieTrendingDao()

    @Provides
    fun provideRemoteKeyDao(database: MovieDatabase): RemoteKeyDao = database.remoteKeyDao()

    @Provides
    fun provideSearchDao(database: MovieDatabase): SearchDao = database.searchDao()

    @Provides
    fun provideMoviePopularDao(database: MovieDatabase): MoviePopularDao = database.moviePopular()
}