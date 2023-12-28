package com.novandi.movieverse.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.novandi.movieverse.data.paging.MovieReviewPagingSource
import com.novandi.movieverse.data.response.NetworkBoundResource
import com.novandi.movieverse.data.response.NetworkOnlyResource
import com.novandi.movieverse.data.response.Resource
import com.novandi.movieverse.data.source.local.LocalDataSource
import com.novandi.movieverse.data.source.remote.RemoteDataSource
import com.novandi.movieverse.data.source.remote.network.ApiResponse
import com.novandi.movieverse.data.source.remote.response.MovieDetailResponse
import com.novandi.movieverse.data.source.remote.response.MovieImagesResponse
import com.novandi.movieverse.data.source.remote.response.MovieResponseItems
import com.novandi.movieverse.domain.model.Movie
import com.novandi.movieverse.domain.model.MovieDetail
import com.novandi.movieverse.domain.model.MovieDetailImages
import com.novandi.movieverse.domain.model.MoviewReviewItem
import com.novandi.movieverse.domain.repository.MovieRepository
import com.novandi.movieverse.utils.AppExecutors
import com.novandi.movieverse.utils.mappers.MovieMappers
import com.novandi.movieverse.utils.MovieType
import com.novandi.movieverse.utils.mappers.MovieDetailMappers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : MovieRepository {
    override fun getUpcomingMovies() : Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponseItems>>() {
            override fun loadFromDB() : Flow<List<Movie>> =
                localDataSource.getMovies(MovieType.UPCOMING).map {
                    MovieMappers.mapEntitiesToDomain(it)
                }

            override fun shouldFetch(data: List<Movie>?) : Boolean = data.isNullOrEmpty()

            override suspend fun createCall() : Flow<ApiResponse<List<MovieResponseItems>>> =
                remoteDataSource.getUpcomingMovies()

            override suspend fun saveCallResult(data: List<MovieResponseItems>) {
                val movies = MovieMappers.mapResponsesToEntities(data, MovieType.UPCOMING)
                localDataSource.insertMovies(movies)
            }
        }.asFlow()

    override fun getPopularMovies() : Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponseItems>>() {
            override fun loadFromDB() : Flow<List<Movie>> =
                localDataSource.getMovies(MovieType.POPULAR).map {
                    MovieMappers.mapEntitiesToDomain(it)
                }

            override fun shouldFetch(data: List<Movie>?) : Boolean = data.isNullOrEmpty()

            override suspend fun createCall() : Flow<ApiResponse<List<MovieResponseItems>>> =
                remoteDataSource.getPopularMovies()

            override suspend fun saveCallResult(data: List<MovieResponseItems>) {
                val movies = MovieMappers.mapResponsesToEntities(data, MovieType.POPULAR)
                localDataSource.insertMovies(movies)
            }
        }.asFlow()

    override fun getNowPlayingMovies() : Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponseItems>>() {
            override fun loadFromDB() : Flow<List<Movie>> =
                localDataSource.getMovies(MovieType.NOW_PLAYING).map {
                    MovieMappers.mapEntitiesToDomain(it)
                }

            override fun shouldFetch(data: List<Movie>?) : Boolean = data.isNullOrEmpty()

            override suspend fun createCall() : Flow<ApiResponse<List<MovieResponseItems>>> =
                remoteDataSource.getNowPlayingMovies()

            override suspend fun saveCallResult(data: List<MovieResponseItems>) {
                val movies = MovieMappers.mapResponsesToEntities(data, MovieType.NOW_PLAYING)
                localDataSource.insertMovies(movies)
            }
        }.asFlow()

    override fun getTopRatedMovies() : Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponseItems>>() {
            override fun loadFromDB() : Flow<List<Movie>> =
                localDataSource.getMovies(MovieType.TOP_RATED).map {
                    MovieMappers.mapEntitiesToDomain(it)
                }

            override fun shouldFetch(data: List<Movie>?) : Boolean = data.isNullOrEmpty()

            override suspend fun createCall() : Flow<ApiResponse<List<MovieResponseItems>>> =
                remoteDataSource.getTopRatedMovies()

            override suspend fun saveCallResult(data: List<MovieResponseItems>) {
                val movies = MovieMappers.mapResponsesToEntities(data, MovieType.TOP_RATED)
                localDataSource.insertMovies(movies)
            }
        }.asFlow()

    override fun getTrendingMovies() : Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponseItems>>() {
            override fun loadFromDB() : Flow<List<Movie>> =
                localDataSource.getMovies(MovieType.TRENDING).map {
                    MovieMappers.mapEntitiesToDomain(it)
                }

            override fun shouldFetch(data: List<Movie>?) : Boolean = data.isNullOrEmpty()

            override suspend fun createCall() : Flow<ApiResponse<List<MovieResponseItems>>> =
                remoteDataSource.getTrendingMovies()

            override suspend fun saveCallResult(data: List<MovieResponseItems>) {
                val movies = MovieMappers.mapResponsesToEntities(data, MovieType.TRENDING)
                localDataSource.insertMovies(movies)
            }
        }.asFlow()

    override fun getDiscoverMovies() : Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponseItems>>() {
            override fun loadFromDB() : Flow<List<Movie>> =
                localDataSource.getMovies(MovieType.DISCOVER).map {
                    MovieMappers.mapEntitiesToDomain(it)
                }

            override fun shouldFetch(data: List<Movie>?) : Boolean = data.isNullOrEmpty()

            override suspend fun createCall() : Flow<ApiResponse<List<MovieResponseItems>>> =
                remoteDataSource.getDiscoverMovies()

            override suspend fun saveCallResult(data: List<MovieResponseItems>) {
                val movies = MovieMappers.mapResponsesToEntities(data, MovieType.DISCOVER)
                localDataSource.insertMovies(movies)
            }
        }.asFlow()

    override fun getMovieDetail(movieId: Int) : Flow<Resource<MovieDetail>> =
        object : NetworkOnlyResource<MovieDetail, MovieDetailResponse>() {
            override fun loadFromNetwork(data: MovieDetailResponse): Flow<MovieDetail> =
                MovieDetailMappers.mapResponseToDomain(data)

            override suspend fun createCall(): Flow<ApiResponse<MovieDetailResponse>> =
                remoteDataSource.getMovieDetail(movieId)
        }.asFlow()

    override fun getMovieImages(movieId: Int) : Flow<Resource<List<MovieDetailImages>>> =
        object : NetworkOnlyResource<List<MovieDetailImages>, MovieImagesResponse>() {
            override fun loadFromNetwork(data: MovieImagesResponse): Flow<List<MovieDetailImages>> =
                MovieDetailMappers.mapImagesResponsesToDomain(data)

            override suspend fun createCall(): Flow<ApiResponse<MovieImagesResponse>> =
                remoteDataSource.getMovieImages(movieId)
        }.asFlow()

    override fun getMovieReviews(movieId: Int): Flow<PagingData<MoviewReviewItem>> =
        Pager(
            config = PagingConfig(pageSize = 15),
            pagingSourceFactory = {
                MovieReviewPagingSource(remoteDataSource, movieId)
            }
        ).flow

    override fun getSimilarMovies(movieId: Int): Flow<Resource<List<Movie>>> =
        object : NetworkOnlyResource<List<Movie>, List<MovieResponseItems>>() {
            override fun loadFromNetwork(data: List<MovieResponseItems>): Flow<List<Movie>> =
                MovieMappers.mapResponsesToDomain(data)

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponseItems>>> =
                remoteDataSource.getSimilarMovies(movieId)
        }.asFlow()
}