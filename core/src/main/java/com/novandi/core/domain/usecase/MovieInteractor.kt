package com.novandi.core.domain.usecase

import androidx.paging.PagingData
import com.novandi.core.data.response.Resource
import com.novandi.core.domain.model.Movie
import com.novandi.core.domain.model.MovieDetail
import com.novandi.core.domain.model.MovieDetailImages
import com.novandi.core.domain.model.MoviewReviewItem
import com.novandi.core.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(
    private val movieRepository: MovieRepository
) : MovieUseCase {
    override fun getUpcomingMovies() : Flow<Resource<List<Movie>>> = movieRepository.getUpcomingMovies()
    override fun getPopularMovies() : Flow<Resource<List<Movie>>> = movieRepository.getPopularMovies()
    override fun getNowPlayingMovies() : Flow<Resource<List<Movie>>> = movieRepository.getNowPlayingMovies()
    override fun getTopRatedMovies() : Flow<Resource<List<Movie>>> = movieRepository.getTopRatedMovies()
    override fun getTrendingMovies() : Flow<PagingData<Movie>> = movieRepository.getTrendingMovies()
    override fun getMovieDetail(movieId: Int) : Flow<Resource<MovieDetail>> = movieRepository.getMovieDetail(movieId)
    override fun getMovieImages(movieId: Int) : Flow<Resource<List<MovieDetailImages>>> = movieRepository.getMovieImages(movieId)
    override fun getMovieReviews(movieId: Int): Flow<PagingData<MoviewReviewItem>> = movieRepository.getMovieReviews(movieId)
    override fun getSimilarMovies(movieId: Int): Flow<Resource<List<Movie>>> = movieRepository.getSimilarMovies(movieId)
    override fun getDiscoverMovies(): Flow<Resource<List<Movie>>> = movieRepository.getDiscoverMovies()
    override fun getSearchMovies(query: String): Flow<PagingData<Movie>> = movieRepository.getSearchMovies(query)
    override fun getFavoriteMoviesTotal(accountId: Int): Flow<Resource<Int>> = movieRepository.getFavoriteMoviesTotal(accountId)

    override fun getRatedMoviesTotal(accountId: Int): Flow<Resource<Int>> = movieRepository.getRatedMoviesTotal(accountId)

    override fun getWatchlistMoviesTotal(accountId: Int): Flow<Resource<Int>> = movieRepository.getWatchlistMoviesTotal(accountId)
}