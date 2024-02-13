package com.novandi.core.data.source.remote

import android.util.Log
import com.google.gson.Gson
import com.novandi.core.data.response.ErrorResponse
import com.novandi.core.data.source.remote.network.ApiResponse
import com.novandi.core.data.source.remote.network.ApiService
import com.novandi.core.data.source.remote.response.LoginRequest
import com.novandi.core.data.source.remote.response.AuthResponse
import com.novandi.core.data.source.remote.response.LogoutRequest
import com.novandi.core.data.source.remote.response.MovieDetailResponse
import com.novandi.core.data.source.remote.response.MovieImagesResponse
import com.novandi.core.data.source.remote.response.MovieResponse
import com.novandi.core.data.source.remote.response.MovieResponseItems
import com.novandi.core.data.source.remote.response.MovieReviewsResponse
import com.novandi.core.data.source.remote.response.MovieSearchResponse
import com.novandi.core.data.source.remote.response.RequestTokenResponse
import com.novandi.core.data.source.remote.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
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
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                Gson().fromJson(errorBody, ErrorResponse::class.java)?.message
            } catch (e: Exception) { null }
            emit(ApiResponse.Error(errorMessage ?: "Unknown error"))
            Log.e("RemoteDataSource", errorMessage ?: "Unknown error")
        } catch (e: Exception) {
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
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                Gson().fromJson(errorBody, ErrorResponse::class.java)?.message
            } catch (e: Exception) { null }
            emit(ApiResponse.Error(errorMessage ?: "Unknown error"))
            Log.e("RemoteDataSource", errorMessage ?: "Unknown error")
        } catch (e: Exception) {
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
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                Gson().fromJson(errorBody, ErrorResponse::class.java)?.message
            } catch (e: Exception) { null }
            emit(ApiResponse.Error(errorMessage ?: "Unknown error"))
            Log.e("RemoteDataSource", errorMessage ?: "Unknown error")
        } catch (e: Exception) {
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
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                Gson().fromJson(errorBody, ErrorResponse::class.java)?.message
            } catch (e: Exception) { null }
            emit(ApiResponse.Error(errorMessage ?: "Unknown error"))
            Log.e("RemoteDataSource", errorMessage ?: "Unknown error")
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getMovieDetail(movieId: Int) : Flow<ApiResponse<MovieDetailResponse>> = flow {
        try {
            val response = apiService.getMovieDetail(id = movieId)
            emit(ApiResponse.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                Gson().fromJson(errorBody, ErrorResponse::class.java)?.message
            } catch (e: Exception) { null }
            emit(ApiResponse.Error(errorMessage ?: "Unknown error"))
            Log.e("RemoteDataSource", errorMessage ?: "Unknown error")
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getMovieImages(movieId: Int) : Flow<ApiResponse<MovieImagesResponse>> = flow {
        try {
            val response = apiService.getMovieImages(id = movieId)
            emit(ApiResponse.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                Gson().fromJson(errorBody, ErrorResponse::class.java)?.message
            } catch (e: Exception) { null }
            emit(ApiResponse.Error(errorMessage ?: "Unknown error"))
            Log.e("RemoteDataSource", errorMessage ?: "Unknown error")
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getMovieReviews(movieId: Int, page: Int) : MovieReviewsResponse = apiService.getMovieReviews(id = movieId, page = page)

    suspend fun getSimilarMovies(movieId: Int) : Flow<ApiResponse<List<MovieResponseItems>>> = flow {
        try {
            val response = apiService.getSimilarMovies(movieId)
            if (response.results.isNotEmpty()) {
                emit(ApiResponse.Success(response.results))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                Gson().fromJson(errorBody, ErrorResponse::class.java)?.message
            } catch (e: Exception) { null }
            emit(ApiResponse.Error(errorMessage ?: "Unknown error"))
            Log.e("RemoteDataSource", errorMessage ?: "Unknown error")
        } catch (e: Exception) {
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
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                Gson().fromJson(errorBody, ErrorResponse::class.java)?.message
            } catch (e: Exception) { null }
            emit(ApiResponse.Error(errorMessage ?: "Unknown error"))
            Log.e("RemoteDataSource", errorMessage ?: "Unknown error")
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getSearchMovies(query: String, page: Int) : MovieSearchResponse = apiService.getSearchMovies(query = query, page = page)

    suspend fun getRequestToken() : Flow<ApiResponse<RequestTokenResponse>> = flow {
        try {
            val response = apiService.getRequestToken()
            if (response.success) {
                emit(ApiResponse.Success(response))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                Gson().fromJson(errorBody, ErrorResponse::class.java)?.message
            } catch (e: Exception) { null }
            emit(ApiResponse.Error(errorMessage ?: "Unknown error"))
            Log.e("RemoteDataSource", errorMessage ?: "Unknown error")
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    suspend fun validateAccount(request: LoginRequest) : Flow<ApiResponse<AuthResponse>> = flow {
        try {
            val response = apiService.validateAccount(request)
            if (response.success) {
                emit(ApiResponse.Success(response))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                Gson().fromJson(errorBody, ErrorResponse::class.java)?.message
            } catch (e: Exception) { null }
            emit(ApiResponse.Error(errorMessage ?: "Unknown error"))
            Log.e("RemoteDataSource", errorMessage ?: "Unknown error")
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    suspend fun login(request: LoginRequest) : Flow<ApiResponse<AuthResponse>> = flow {
        try {
            val response = apiService.login(request)
            if (response.success) {
                emit(ApiResponse.Success(response))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                Gson().fromJson(errorBody, ErrorResponse::class.java)?.message
            } catch (e: Exception) { null }
            emit(ApiResponse.Error(errorMessage ?: "Unknown error"))
            Log.e("RemoteDataSource", errorMessage ?: "Unknown error")
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    suspend fun logout(request: LogoutRequest) : Flow<ApiResponse<AuthResponse>> = flow {
        try {
            val response = apiService.logout(request)
            if (response.success) {
                emit(ApiResponse.Success(response))
            } else {
                emit(ApiResponse.Empty)
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                Gson().fromJson(errorBody, ErrorResponse::class.java)?.message
            } catch (e: Exception) { null }
            emit(ApiResponse.Error(errorMessage ?: "Unknown error"))
            Log.e("RemoteDataSource", errorMessage ?: "Unknown error")
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getUser(sessionId: String) : Flow<ApiResponse<UserResponse>> = flow {
        try {
            val response = apiService.getUser(sessionId)
            emit(ApiResponse.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                Gson().fromJson(errorBody, ErrorResponse::class.java)?.message
            } catch (e: Exception) { null }
            emit(ApiResponse.Error(errorMessage ?: "Unknown error"))
            Log.e("RemoteDataSource", errorMessage ?: "Unknown error")
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getFavoriteMovies(accountId: Int) : Flow<ApiResponse<MovieResponse>> = flow {
        try {
            val response = apiService.getFavoriteMovies(accountId)
            emit(ApiResponse.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                Gson().fromJson(errorBody, ErrorResponse::class.java)?.message
            } catch (e: Exception) { null }
            emit(ApiResponse.Error(errorMessage ?: "Unknown error"))
            Log.e("RemoteDataSource", errorMessage ?: "Unknown error")
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getRatedMovies(accountId: Int) : Flow<ApiResponse<MovieResponse>> = flow {
        try {
            val response = apiService.getRatedMovies(accountId)
            emit(ApiResponse.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                Gson().fromJson(errorBody, ErrorResponse::class.java)?.message
            } catch (e: Exception) { null }
            emit(ApiResponse.Error(errorMessage ?: "Unknown error"))
            Log.e("RemoteDataSource", errorMessage ?: "Unknown error")
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getWatchlistMovies(accountId: Int) : Flow<ApiResponse<MovieResponse>> = flow {
        try {
            val response = apiService.getWatchlistMovies(accountId)
            emit(ApiResponse.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = try {
                Gson().fromJson(errorBody, ErrorResponse::class.java)?.message
            } catch (e: Exception) { null }
            emit(ApiResponse.Error(errorMessage ?: "Unknown error"))
            Log.e("RemoteDataSource", errorMessage ?: "Unknown error")
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)
}