package com.novandi.core.data.source.remote

import android.util.Log
import com.novandi.core.data.source.remote.network.ApiResponse
import com.novandi.core.data.source.remote.network.ApiService
import com.novandi.core.data.source.remote.response.MovieDetailResponse
import com.novandi.core.data.source.remote.response.MovieImagesResponse
import com.novandi.core.data.source.remote.response.MovieResponseItems
import com.novandi.core.data.source.remote.response.MovieReviewsResponse
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

    suspend fun getMovieDetail(movieId: Int) : Flow<ApiResponse<MovieDetailResponse>> = flow {
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

    suspend fun getMovieReviews(movieId: Int, page: Int): MovieReviewsResponse = apiService.getMovieReviews(id = movieId, page = page)

    suspend fun getSimilarMovies(movieId: Int) : Flow<ApiResponse<List<MovieResponseItems>>> = flow {
        try {
            val response = apiService.getSimilarMovies(movieId)
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
}