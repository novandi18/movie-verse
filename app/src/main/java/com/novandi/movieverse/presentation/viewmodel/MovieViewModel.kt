package com.novandi.movieverse.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.novandi.core.data.response.Resource
import com.novandi.core.data.source.remote.response.FavoriteRequest
import com.novandi.core.data.source.remote.response.WatchlistRequest
import com.novandi.core.data.store.DataStoreManager
import com.novandi.core.domain.model.GeneralResult
import com.novandi.core.domain.model.Movie
import com.novandi.core.domain.model.MoviewReviewItem
import com.novandi.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _movieReviews: MutableStateFlow<PagingData<MoviewReviewItem>> = MutableStateFlow(value = PagingData.empty())
    val movieReviews: MutableStateFlow<PagingData<MoviewReviewItem>> get() = _movieReviews

    private val _similarMovies: MutableLiveData<Resource<List<Movie>>> = MutableLiveData(Resource.Loading())
    val similarMovies: MutableLiveData<Resource<List<Movie>>> get() = _similarMovies

    private val _isFavorite = MutableStateFlow<Resource<Boolean>?>(null)
    val isFavorite: StateFlow<Resource<Boolean>?> get() = _isFavorite

    private val _isWatchlist = MutableStateFlow<Resource<Boolean>?>(null)
    val isWatchlist: StateFlow<Resource<Boolean>?> get() = _isWatchlist

    private val _updateFavorite = MutableStateFlow<Resource<GeneralResult>?>(null)
    val updateFavorite: StateFlow<Resource<GeneralResult>?> get() = _updateFavorite

    private val _updateWatchlist = MutableStateFlow<Resource<GeneralResult>?>(null)
    val updateWatchlist: StateFlow<Resource<GeneralResult>?> get() = _updateWatchlist

    val accountId = dataStoreManager.accountId.asLiveData()

    var favorite by mutableStateOf(false)
        private set

    var watchlist by mutableStateOf(false)
        private set

    fun getMovie(movieId: Int) = movieUseCase.getMovieDetail(movieId)
    fun getMovieImages(movieId: Int) = movieUseCase.getMovieImages(movieId)
    fun getMovieReviews(movieId: Int) {
        viewModelScope.launch {
            movieUseCase.getMovieReviews(movieId)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect { result ->
                    _movieReviews.value = result
                }
        }
    }
    fun getSimilarMovies(movieId: Int) {
        viewModelScope.launch {
            movieUseCase.getSimilarMovies(movieId)
                .catch {
                    _similarMovies.value = Resource.Error(it.message.toString())
                }
                .collect { result ->
                    _similarMovies.value = result
                }
        }
    }

    fun onFavoriteChange(value: Boolean) {
        favorite = value
    }

    fun onWatchlistChange(value: Boolean) {
        watchlist = value
    }

    fun getIsFavorite(accountId: Int, movieId: Int) {
        viewModelScope.launch {
            movieUseCase.getIsFavorite(accountId, movieId)
                .catch { err ->
                    _isFavorite.value = Resource.Error(err.message.toString())
                }
                .collect { result ->
                    _isFavorite.value = result
                }
        }
    }

    fun getIsWatchlist(accountId: Int, movieId: Int) {
        viewModelScope.launch {
            movieUseCase.getIsWatchlist(accountId, movieId)
                .catch { err ->
                    _isWatchlist.value = Resource.Error(err.message.toString())
                }
                .collect { result ->
                    _isWatchlist.value = result
                }
        }
    }

    fun updateFavorite(accountId: Int, request: FavoriteRequest) {
        viewModelScope.launch {
            movieUseCase.updateFavorite(accountId, request)
                .catch { err ->
                    _updateFavorite.value = Resource.Error(err.message.toString())
                }
                .collect { result ->
                    _updateFavorite.value = result
                }
        }
    }

    fun updateWatchlist(accountId: Int, request: WatchlistRequest) {
        viewModelScope.launch {
            movieUseCase.updateWatchlist(accountId, request)
                .catch { err ->
                    _updateWatchlist.value = Resource.Error(err.message.toString())
                }
                .collect { result ->
                    _updateWatchlist.value = result
                }
        }
    }
}