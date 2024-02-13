package com.novandi.core.domain.repository

import androidx.paging.PagingData
import com.novandi.core.data.response.Resource
import com.novandi.core.domain.model.Movie
import com.novandi.core.domain.model.MovieDetail
import com.novandi.core.domain.model.MovieDetailImages
import com.novandi.core.domain.model.MoviewReviewItem
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getUpcomingMovies(): Flow<Resource<List<Movie>>>
    fun getPopularMovies(): Flow<Resource<List<Movie>>>
    fun getNowPlayingMovies(): Flow<Resource<List<Movie>>>
    fun getTopRatedMovies(): Flow<Resource<List<Movie>>>
    fun getTrendingMovies(): Flow<PagingData<Movie>>
    fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>>
    fun getMovieImages(movieId: Int): Flow<Resource<List<MovieDetailImages>>>
    fun getMovieReviews(movieId: Int): Flow<PagingData<MoviewReviewItem>>
    fun getSimilarMovies(movieId: Int): Flow<Resource<List<Movie>>>
    fun getDiscoverMovies(): Flow<Resource<List<Movie>>>
    fun getSearchMovies(query: String): Flow<PagingData<Movie>>
    fun getFavoriteMoviesTotal(accountId: Int): Flow<Resource<Int>>
    fun getRatedMoviesTotal(accountId: Int): Flow<Resource<Int>>
    fun getWatchlistMoviesTotal(accountId: Int): Flow<Resource<Int>>
}