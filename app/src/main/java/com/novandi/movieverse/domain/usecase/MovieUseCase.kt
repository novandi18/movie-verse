package com.novandi.movieverse.domain.usecase

import com.novandi.movieverse.data.response.Resource
import com.novandi.movieverse.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getUpcomingMovies(): Flow<Resource<List<Movie>>>
    fun getPopularMovies(): Flow<Resource<List<Movie>>>
}