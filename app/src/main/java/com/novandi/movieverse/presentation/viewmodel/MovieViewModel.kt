package com.novandi.movieverse.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.novandi.movieverse.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {
    fun getMovie(movieId: Int) = movieUseCase.getMovieDetail(movieId)
    fun getMovieImages(movieId: Int) = movieUseCase.getMovieImages(movieId)
}