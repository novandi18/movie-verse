package com.novandi.movieverse.data.source.remote.network

import com.novandi.movieverse.data.source.remote.response.MovieResponse
import retrofit2.http.GET

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies() : MovieResponse

    @GET("movie/popular")
    suspend fun getPopularMovies() : MovieResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies() : MovieResponse
}