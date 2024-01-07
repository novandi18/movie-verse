package com.novandi.movieverse.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.novandi.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    movieUseCase: MovieUseCase
) : ViewModel() {
    val upcomingMovies = movieUseCase.getUpcomingMovies().asLiveData()
    val discoverMovies = movieUseCase.getDiscoverMovies().asLiveData()
    val nowPlayingMovies = movieUseCase.getNowPlayingMovies().asLiveData()
    val topRatedMovies = movieUseCase.getTopRatedMovies().asLiveData()
}