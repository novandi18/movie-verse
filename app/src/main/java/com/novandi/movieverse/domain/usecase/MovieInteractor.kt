package com.novandi.movieverse.domain.usecase

import androidx.paging.PagingData
import com.novandi.movieverse.data.response.Resource
import com.novandi.movieverse.domain.model.Movie
import com.novandi.movieverse.domain.model.MovieDetail
import com.novandi.movieverse.domain.model.MovieDetailImages
import com.novandi.movieverse.domain.model.MoviewReviewItem
import com.novandi.movieverse.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(
    private val movieRepository: MovieRepository
) : MovieUseCase {
    override fun getUpcomingMovies() : Flow<Resource<List<Movie>>> = movieRepository.getUpcomingMovies()
    override fun getPopularMovies() : Flow<Resource<List<Movie>>> = movieRepository.getPopularMovies()
    override fun getNowPlayingMovies() : Flow<Resource<List<Movie>>> = movieRepository.getNowPlayingMovies()
    override fun getTopRatedMovies() : Flow<Resource<List<Movie>>> = movieRepository.getTopRatedMovies()
    override fun getTrendingMovies() : Flow<Resource<List<Movie>>> = movieRepository.getTrendingMovies()
    override fun getMovieDetail(movieId: Int) : Flow<Resource<MovieDetail>> = movieRepository.getMovieDetail(movieId)
    override fun getMovieImages(movieId: Int) : Flow<Resource<List<MovieDetailImages>>> = movieRepository.getMovieImages(movieId)

    override fun getMovieReviews(movieId: Int): Flow<PagingData<MoviewReviewItem>> = movieRepository.getMovieReviews(movieId)
    override fun getSimilarMovies(movieId: Int): Flow<Resource<List<Movie>>> = movieRepository.getSimilarMovies(movieId)
}