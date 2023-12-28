package com.novandi.movieverse.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.novandi.movieverse.data.response.Resource
import com.novandi.movieverse.domain.model.Movie
import com.novandi.movieverse.domain.model.MoviewReviewItem
import com.novandi.movieverse.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {
    private val _movieReviews: MutableStateFlow<PagingData<MoviewReviewItem>> = MutableStateFlow(value = PagingData.empty())
    val movieReviews: MutableStateFlow<PagingData<MoviewReviewItem>> get() = _movieReviews

    private val _similarMovies: MutableLiveData<Resource<List<Movie>>> = MutableLiveData(Resource.Loading())
    val similarMovies: MutableLiveData<Resource<List<Movie>>> get() = _similarMovies

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
}