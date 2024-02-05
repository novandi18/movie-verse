package com.novandi.core.data.source.local

import com.novandi.core.data.source.local.entity.MovieEntity
import com.novandi.core.data.source.local.entity.SearchEntity
import com.novandi.core.data.source.local.room.dao.MovieDao
import com.novandi.core.data.source.local.room.dao.SearchDao
import com.novandi.core.utils.MovieType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val movieDao: MovieDao,
    private val searchDao: SearchDao
) {
    fun getMovies(movieType: MovieType): Flow<List<MovieEntity>> = movieDao.getMovies(movieType.name)
    suspend fun insertMovies(movies: List<MovieEntity>) = movieDao.insertMovie(movies)
    fun getSearch(): Flow<List<SearchEntity>> = searchDao.getSearch()
    fun saveSearch(search: SearchEntity) = searchDao.saveSearch(search)
    fun deleteSearch(id: Int) = searchDao.deleteSearch(id)
}