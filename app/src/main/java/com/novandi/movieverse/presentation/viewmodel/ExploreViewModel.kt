package com.novandi.movieverse.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.novandi.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    movieUseCase: MovieUseCase
) : ViewModel() {
    val popularMovies = movieUseCase.getPopularMovies().asLiveData()
}