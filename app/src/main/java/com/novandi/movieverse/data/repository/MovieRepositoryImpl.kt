package com.novandi.movieverse.data.repository

import com.novandi.movieverse.data.response.NetworkBoundResource
import com.novandi.movieverse.data.response.Resource
import com.novandi.movieverse.data.source.local.LocalDataSource
import com.novandi.movieverse.data.source.remote.RemoteDataSource
import com.novandi.movieverse.data.source.remote.network.ApiResponse
import com.novandi.movieverse.data.source.remote.response.MovieResponseItems
import com.novandi.movieverse.domain.model.Movie
import com.novandi.movieverse.domain.repository.MovieRepository
import com.novandi.movieverse.utils.AppExecutors
import com.novandi.movieverse.utils.DataMappers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : MovieRepository {
    override fun getUpcomingMovies(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponseItems>>() {
            override fun loadFromDB(): Flow<List<Movie>> =
                localDataSource.getUpcomingMovies().map {
                    DataMappers.mapEntitiesToDomain(it)
                }

            override fun shouldFetch(data: List<Movie>?): Boolean = true

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponseItems>>> =
                remoteDataSource.getUpcomingMovies()

            override suspend fun saveCallResult(data: List<MovieResponseItems>) {
                val movies = DataMappers.mapResponsesToEntities(data, "upcoming")
                localDataSource.insertMovies(movies)
            }
        }.asFlow()

    override fun getPopularMovies(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponseItems>>() {
            override fun loadFromDB(): Flow<List<Movie>> =
                localDataSource.getUpcomingMovies().map {
                    DataMappers.mapEntitiesToDomain(it)
                }

            override fun shouldFetch(data: List<Movie>?): Boolean = true

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponseItems>>> =
                remoteDataSource.getPopularMovies()

            override suspend fun saveCallResult(data: List<MovieResponseItems>) {
                val movies = DataMappers.mapResponsesToEntities(data, "popular")
                localDataSource.insertMovies(movies)
            }
        }.asFlow()
}