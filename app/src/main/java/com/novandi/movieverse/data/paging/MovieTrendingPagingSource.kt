package com.novandi.movieverse.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.novandi.movieverse.data.source.remote.RemoteDataSource
import com.novandi.movieverse.domain.model.Movie
import com.novandi.movieverse.utils.mappers.MovieMappers

class MovieTrendingPagingSource(
    private val remoteDataSource: RemoteDataSource,
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
            val response = remoteDataSource.getTrendingMovies(page = page)
            val result = MovieMappers.mapPagingResponsesToDomain(response.results)

            LoadResult.Page(
                data = result,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.results.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}