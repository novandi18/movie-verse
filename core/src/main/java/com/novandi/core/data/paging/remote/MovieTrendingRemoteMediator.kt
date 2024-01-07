package com.novandi.core.data.paging.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.novandi.core.data.source.local.entity.MovieTrendingEntity
import com.novandi.core.data.source.local.entity.RemoteKeyEntity
import com.novandi.core.data.source.local.room.MovieDatabase
import com.novandi.core.data.source.remote.network.ApiService
import com.novandi.core.utils.mappers.MovieMappers
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieTrendingRemoteMediator @Inject constructor(
    private val apiService: ApiService,
    private val movieDatabase: MovieDatabase
): RemoteMediator<Int, MovieTrendingEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieTrendingEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> INITIAL_PAGE
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKey()
                remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
        }

        return try {
            val response = apiService.getTrendingMovies(page = page)
            val movies = response.results
            val endOfPaginationReached = movies.isEmpty()

            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDatabase.remoteKeyDao().deleteAll()
                    movieDatabase.movieTrendingDao().clearAll()
                }
                val nextKey = if (endOfPaginationReached || page > 500) null else page + 1
                val remoteKeys = RemoteKeyEntity(id = REMOTE_ID, currentPage = page, nextKey = nextKey)
                movieDatabase.remoteKeyDao().insert(remoteKeys)
                movieDatabase.movieTrendingDao().insertMovie(
                    MovieMappers.mapTrendingResponsesToEntity(movies, response.page)
                )
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached || page > 500)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    private suspend fun getRemoteKey(): RemoteKeyEntity? {
        return movieDatabase.withTransaction { movieDatabase.remoteKeyDao().getById(REMOTE_ID) }
    }

    companion object {
        private const val REMOTE_ID = "remote_id"
        private const val INITIAL_PAGE = 1
    }
}