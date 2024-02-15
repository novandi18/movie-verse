package com.novandi.core.domain.usecase

import androidx.paging.PagingData
import com.novandi.core.data.response.Resource
import com.novandi.core.domain.model.Movie
import com.novandi.core.domain.model.MovieDetail
import com.novandi.core.domain.model.MovieDetailImages
import com.novandi.core.domain.model.MoviewReviewItem
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getUpcomingMovies() : Flow<Resource<List<Movie>>>
    fun getPopularMovies() : Flow<Resource<List<Movie>>>
    fun getNowPlayingMovies() : Flow<Resource<List<Movie>>>
    fun getTopRatedMovies() : Flow<Resource<List<Movie>>>
    fun getTrendingMovies() : Flow<PagingData<Movie>>
    fun getMovieDetail(movieId: Int) : Flow<Resource<MovieDetail>>
    fun getMovieImages(movieId: Int) : Flow<Resource<List<MovieDetailImages>>>
    fun getMovieReviews(movieId: Int) : Flow<PagingData<MoviewReviewItem>>
    fun getSimilarMovies(movieId: Int) : Flow<Resource<List<Movie>>>
    fun getDiscoverMovies() : Flow<Resource<List<Movie>>>
    fun getSearchMovies(query: String) : Flow<PagingData<Movie>>
    fun getFavoriteMoviesTotal(accountId: Int): Flow<Resource<Int>>
    fun getRatedMoviesTotal(accountId: Int): Flow<Resource<Int>>
    fun getWatchlistMoviesTotal(accountId: Int): Flow<Resource<Int>>
    fun getIsFavorite(accountId: Int, movieId: Int): Flow<Resource<Boolean>>
    fun getIsWatchlist(accountId: Int, movieId: Int): Flow<Resource<Boolean>>
}