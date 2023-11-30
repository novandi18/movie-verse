package com.novandi.movieverse.domain.usecase

import com.novandi.movieverse.data.response.Resource
import com.novandi.movieverse.domain.model.Movie
import com.novandi.movieverse.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(
    private val movieRepository: MovieRepository
) : MovieUseCase {
    override fun getUpcomingMovies(): Flow<Resource<List<Movie>>> = movieRepository.getUpcomingMovies()

    override fun getPopularMovies(): Flow<Resource<List<Movie>>> = movieRepository.getPopularMovies()
}