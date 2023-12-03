package com.novandi.movieverse.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.novandi.movieverse.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(
    movieUseCase: MovieUseCase
) : ViewModel() {
    val trendingMovies = movieUseCase.getTrendingMovies().asLiveData()
}