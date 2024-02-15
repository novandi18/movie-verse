package com.novandi.movieverse.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.novandi.core.data.store.DataStoreManager
import com.novandi.core.domain.model.Movie
import com.novandi.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    private val dataStoreManager: DataStoreManager
): ViewModel() {
    private val _movies: MutableStateFlow<PagingData<Movie>> = MutableStateFlow(value = PagingData.empty())
    val movies: MutableStateFlow<PagingData<Movie>> get() = _movies

    init {
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch {
            dataStoreManager.accountId.collect { accountId ->
                movieUseCase.getWatchlistMovies(accountId!!.toInt())
                    .cachedIn(viewModelScope)
                    .collect { result ->
                        _movies.value = result
                    }
            }
        }
    }
}