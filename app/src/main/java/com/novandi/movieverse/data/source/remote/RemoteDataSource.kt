package com.novandi.movieverse.data.source.remote

import android.util.Log
import com.novandi.movieverse.data.source.remote.network.ApiResponse
import com.novandi.movieverse.data.source.remote.network.ApiService
import com.novandi.movieverse.data.source.remote.response.MovieDetailResponseAlt
import com.novandi.movieverse.data.source.remote.response.MovieImagesResponse
import com.novandi.movieverse.data.source.remote.response.MovieResponseItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getUpcomingMovies() : Flow<ApiResponse<List<MovieResponseItems>>> = flow {
        try {
            val response = apiService.getUpcomingMovies()
            if (response.results.isNotEmpty()) {
                emit(ApiResponse.Success(response.results))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e : Exception){
            emit(ApiResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getPopularMovies() : Flow<ApiResponse<List<MovieResponseItems>>> = flow {
        try {
            val response = apiService.getPopularMovies()
            if (response.results.isNotEmpty()) {
                emit(ApiResponse.Success(response.results))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e : Exception){
            emit(ApiResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getNowPlayingMovies() : Flow<ApiResponse<List<MovieResponseItems>>> = flow {
        try {
            val response = apiService.getNowPlayingMovies()
            if (response.results.isNotEmpty()) {
                emit(ApiResponse.Success(response.results))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e : Exception){
            emit(ApiResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getTopRatedMovies() : Flow<ApiResponse<List<MovieResponseItems>>> = flow {
        try {
            val response = apiService.getTopRatedMovies()
            if (response.results.isNotEmpty()) {
                emit(ApiResponse.Success(response.results))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e : Exception){
            emit(ApiResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getTrendingMovies() : Flow<ApiResponse<List<MovieResponseItems>>> = flow {
        try {
            val response = apiService.getTrendingMovies()
            if (response.results.isNotEmpty()) {
                emit(ApiResponse.Success(response.results))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e : Exception){
            emit(ApiResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getDiscoverMovies() : Flow<ApiResponse<List<MovieResponseItems>>> = flow {
        try {
            val response = apiService.getDiscoverMovies()
            if (response.results.isNotEmpty()) {
                emit(ApiResponse.Success(response.results))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e : Exception){
            emit(ApiResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getDiscoverTv() : Flow<ApiResponse<List<MovieResponseItems>>> = flow {
        try {
            val response = apiService.getDiscoverTv()
            if (response.results.isNotEmpty()) {
                emit(ApiResponse.Success(response.results))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e : Exception){
            emit(ApiResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getMovieDetail(movieId: Int) : Flow<ApiResponse<MovieDetailResponseAlt>> = flow {
        try {
            val response = apiService.getMovieDetail(id = movieId)
            emit(ApiResponse.Success(response))
        } catch (e : Exception){
            emit(ApiResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getMovieImages(movieId: Int) : Flow<ApiResponse<MovieImagesResponse>> = flow {
        try {
            val response = apiService.getMovieImages(id = movieId)
            emit(ApiResponse.Success(response))
        } catch (e : Exception){
            emit(ApiResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)
}