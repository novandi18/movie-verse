package com.novandi.movieverse.di

import android.content.Context
import androidx.room.Room
import com.novandi.movieverse.data.source.local.room.MovieDatabase
import com.novandi.movieverse.data.source.local.room.dao.MovieDao
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
    fun provideDatabase(@ApplicationContext context: Context) : MovieDatabase =
        Room.databaseBuilder(
            context, MovieDatabase::class.java, "Movie.db"
        ).build()

    @Provides
    fun provideMovieDao(database: MovieDatabase) : MovieDao =
        database.movieDao()
}