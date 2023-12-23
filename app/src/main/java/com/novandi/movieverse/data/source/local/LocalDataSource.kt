package com.novandi.movieverse.data.source.local

import com.novandi.movieverse.data.source.local.entity.MovieEntity
import com.novandi.movieverse.data.source.local.room.dao.MovieDao
import com.novandi.movieverse.utils.MovieType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val movieDao: MovieDao,
) {
    fun getMovies(movieType: MovieType) : Flow<List<MovieEntity>> = movieDao.getMovies(movieType.name)
    suspend fun insertMovies(movies: List<MovieEntity>) = movieDao.insertMovie(movies)
}