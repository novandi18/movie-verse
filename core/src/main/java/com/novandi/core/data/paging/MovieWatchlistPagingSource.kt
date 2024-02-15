package com.novandi.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.novandi.core.data.source.remote.RemoteDataSource
import com.novandi.core.domain.model.Movie
import com.novandi.core.utils.mappers.MovieMappers

class MovieWatchlistPagingSource(
    private val remoteDataSource: RemoteDataSource,
    private val accountId: Int
): PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = remoteDataSource.getWatchlistMoviesPager(accountId, page)
            val result = MovieMappers.mapPagingResponseToDomain(response.results)

            LoadResult.Page(
                data = result,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.results.isEmpty()) null else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}