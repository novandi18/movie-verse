package com.novandi.core.data.source.remote.network

import com.novandi.core.data.source.remote.response.MovieDetailResponse
import com.novandi.core.data.source.remote.response.MovieImagesResponse
import com.novandi.core.data.source.remote.response.MovieResponse
import com.novandi.core.data.source.remote.response.MovieReviewsResponse
import com.novandi.core.data.source.remote.response.MovieSearchResponse
import retrofit2.http.GET
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
    ): MovieResponse

    @GET("search/movie")
    suspend fun getSearchMovies(
        @Query("query") query: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ) : MovieSearchResponse
}