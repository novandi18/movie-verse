package com.novandi.movieverse.data.source.local

import com.novandi.movieverse.data.source.local.entity.MovieEntity
import com.novandi.movieverse.data.source.local.room.dao.MovieDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val movieDao: MovieDao
) {
    fun getUpcomingMovies(): Flow<List<MovieEntity>> = movieDao.getUpcomingMovies()

    fun getPopularMovies(): Flow<List<MovieEntity>> = movieDao.getPopularMovies()

    suspend fun insertMovies(movies: List<MovieEntity>) = movieDao.insertMovie(movies)
}