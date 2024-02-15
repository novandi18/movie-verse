package com.novandi.core.data.source.remote.network

import com.novandi.core.data.source.remote.response.LoginRequest
import com.novandi.core.data.source.remote.response.AuthResponse
import com.novandi.core.data.source.remote.response.FavoriteRequest
import com.novandi.core.data.source.remote.response.LogoutRequest
import com.novandi.core.data.source.remote.response.MovieDetailResponse
import com.novandi.core.data.source.remote.response.MovieImagesResponse
import com.novandi.core.data.source.remote.response.MovieResponse
import com.novandi.core.data.source.remote.response.MovieReviewsResponse
import com.novandi.core.data.source.remote.response.MovieSearchResponse
import com.novandi.core.data.source.remote.response.RequestTokenResponse
import com.novandi.core.data.source.remote.response.UserResponse
import com.novandi.core.data.source.remote.response.WatchlistRequest
import com.novandi.core.data.source.remote.response.GeneralResponse
import com.novandi.core.data.source.remote.response.RatingRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ) : MovieResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ) : MovieResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ) : MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ) : MovieResponse

    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovies(
        @Path("time_window") timeWindow: String = "day",
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ) : MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") id: Int,
        @Query("language") language: String = "en-US"
    ) : MovieDetailResponse

    @GET("movie/{movie_id}/images")
    suspend fun getMovieImages(
        @Path("movie_id") id: Int,
        @Query("language") language: String = "en-US",
        @Query("include_image_language") includeImageLanguage: String = "en"
    ) : MovieImagesResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") id: Int,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ) : MovieReviewsResponse

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") id: Int,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ) : MovieResponse

    @GET("discover/movie")
    suspend fun getDiscoverMovies(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ) : MovieResponse

    @GET("search/movie")
    suspend fun getSearchMovies(
        @Query("query") query: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ) : MovieSearchResponse

    @GET("authentication/token/new")
    suspend fun getRequestToken() : RequestTokenResponse

    @POST("authentication/token/validate_with_login")
    suspend fun validateAccount(
        @Body request: LoginRequest
    ) : AuthResponse

    @POST("authentication/session/new")
    suspend fun login(
        @Body request: LoginRequest
    ) : AuthResponse

    @HTTP(method = "DELETE", path = "authentication/session", hasBody = true)
    suspend fun logout(
        @Body request: LogoutRequest
    ) : AuthResponse

    @GET("account")
    suspend fun getUser(
        @Query("session_id") sessionId: String
    ) : UserResponse

    @GET("account/{account_id}/favorite/movies")
    suspend fun getFavoriteMovies(
        @Path("account_id") accountId: Int,
        @Query("page") page: Int = 1
    ) : MovieResponse

    @GET("account/{account_id}/rated/movies")
    suspend fun getRatedMovies(
        @Path("account_id") accountId: Int,
        @Query("page") page: Int = 1
    ) : MovieResponse

    @GET("account/{account_id}/watchlist/movies")
    suspend fun getWatchlistMovies(
        @Path("account_id") accountId: Int,
        @Query("page") page: Int = 1
    ) : MovieResponse

    @POST("account/{account_id}/favorite")
    suspend fun updateFavoriteMovie(
        @Path("account_id") accountId: Int,
        @Body request: FavoriteRequest
    ) : GeneralResponse

    @POST("account/{account_id}/watchlist")
    suspend fun updateWatchlistMovie(
        @Path("account_id") accountId: Int,
        @Body request: WatchlistRequest
    ) : GeneralResponse

    @POST("movie/{movie_id}/rating")
    suspend fun addRatingMovie(
        @Path("movie_id") movieId: Int,
        @Query("session_id") sessionId: String,
        @Body request: RatingRequest
    ) : GeneralResponse

    @DELETE("movie/{movie_id}/rating")
    suspend fun deleteRatingMovie(
        @Path("movie_id") movieId: Int,
        @Query("session_id") sessionId: String
    ) : GeneralResponse
}