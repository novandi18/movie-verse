package com.novandi.core.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.novandi.core.data.paging.remote.MovieTrendingRemoteMediator
import com.novandi.core.data.source.local.entity.MovieTrendingEntity
import com.novandi.core.data.source.local.room.MovieDatabase
import com.novandi.core.data.source.remote.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideMovieTrendingPager(
        movieDatabase: MovieDatabase,
        apiService: ApiService
    ): Pager<Int, MovieTrendingEntity> =
        Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 10, initialLoadSize = 20),
            remoteMediator = MovieTrendingRemoteMediator(apiService, movieDatabase),
            pagingSourceFactory = {
                movieDatabase.movieTrendingDao().getMovies()
            }
        )
}