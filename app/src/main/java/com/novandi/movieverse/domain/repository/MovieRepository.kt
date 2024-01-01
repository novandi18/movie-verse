package com.novandi.movieverse.domain.repository

import androidx.paging.PagingData
import com.novandi.movieverse.data.response.Resource
import com.novandi.movieverse.domain.model.Movie
import com.novandi.movieverse.domain.model.MovieDetail
import com.novandi.movieverse.domain.model.MovieDetailImages
import com.novandi.movieverse.domain.model.MoviewReviewItem
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getUpcomingMovies() : Flow<Resource<List<Movie>>>
    fun getPopularMovies() : Flow<Resource<List<Movie>>>
    fun getNowPlayingMovies() : Flow<Resource<List<Movie>>>
    fun getTopRatedMovies() : Flow<Resource<List<Movie>>>
    fun getTrendingMovies() : Flow<Resource<List<Movie>>>
    fun getMovieDetail(movieId: Int) : Flow<Resource<MovieDetail>>
    fun getMovieImages(movieId: Int) : Flow<Resource<List<MovieDetailImages>>>
    fun getMovieReviews(movieId: Int) : Flow<PagingData<MoviewReviewItem>>
    fun getSimilarMovies(movieId: Int) : Flow<Resource<List<Movie>>>
}