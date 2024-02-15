package com.novandi.movieverse.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.novandi.core.data.source.remote.response.RatingRequest
import com.novandi.core.data.source.remote.response.WatchlistRequest
import com.novandi.core.data.store.DataStoreManager
import com.novandi.core.domain.model.GeneralResult
import com.novandi.core.domain.model.Movie
import com.novandi.core.domain.model.MoviewReviewItem
import com.novandi.core.domain.model.RatedMovie
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

    private val _ratingMovie = MutableStateFlow<Resource<RatedMovie>?>(null)
    val ratingMovie: StateFlow<Resource<RatedMovie>?> get() = _ratingMovie

    private val _updateRating = MutableStateFlow<Resource<GeneralResult>?>(null)
    val updateRating: StateFlow<Resource<GeneralResult>?> get() = _updateRating

    private val _deleteRating = MutableStateFlow<Resource<GeneralResult>?>(null)
    val deleteRating: StateFlow<Resource<GeneralResult>?> get() = _deleteRating

    val accountId = dataStoreManager.accountId.asLiveData()

    val sessionId = dataStoreManager.sessionId.asLiveData()

    var favorite by mutableStateOf(false)
        private set

    var watchlist by mutableStateOf(false)
        private set

    var rating by mutableIntStateOf(0)
        private set

    var ratingLoading by mutableStateOf(true)
        private set

    var ratingPage by mutableIntStateOf(1)
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

    fun onRatingChange(rate: Int) {
        rating = rate
    }

    fun onRatingLoadingChange(isLoading: Boolean) {
        ratingLoading = isLoading
    }

    fun onRatingPageChange(page: Int) {
        ratingPage = page
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

    fun getMovieRating(accountId: Int, page: Int, movieId: Int) {
        viewModelScope.launch {
            movieUseCase.getRatedMovie(accountId, page, movieId)
                .catch { err ->
                    _ratingMovie.value = Resource.Error(err.message.toString())
                }
                .collect { result ->
                    _ratingMovie.value = result
                }
        }
    }

    fun updateRating(movieId: Int, sessionId: String, request: RatingRequest) {
        viewModelScope.launch {
            movieUseCase.addRating(movieId, sessionId, request)
                .catch { err ->
                    _updateRating.value = Resource.Error(err.message.toString())
                }
                .collect { result ->
                    onRatingChange(request.value.toInt())
                    _updateRating.value = result
                }
        }
    }

    fun deleteRating(movieId: Int, sessionId: String) {
        viewModelScope.launch {
            movieUseCase.deleteRating(movieId, sessionId)
                .catch { err ->
                    _deleteRating.value = Resource.Error(err.message.toString())
                }
                .collect { result ->
                    onRatingChange(0)
                    _deleteRating.value = result
                }
        }
    }
}