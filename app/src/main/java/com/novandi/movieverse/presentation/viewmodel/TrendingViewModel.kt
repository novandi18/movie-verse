package com.novandi.movieverse.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.novandi.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(
    movieUseCase: MovieUseCase
) : ViewModel() {
    val trendingMovies = movieUseCase.getTrendingMovies().distinctUntilChanged().cachedIn(viewModelScope)
}