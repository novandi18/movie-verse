package com.novandi.movieverse.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.novandi.movieverse.data.source.remote.RemoteDataSource
import com.novandi.movieverse.domain.model.MoviewReviewItem
import com.novandi.movieverse.utils.mappers.MovieDetailMappers


class MovieReviewPagingSource(
    private val remoteDataSource: RemoteDataSource,
    private val movieId: Int
) : PagingSource<Int, MoviewReviewItem>() {
    override fun getRefreshKey(state: PagingState<Int, MoviewReviewItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviewReviewItem> {
        return try {
            val page = params.key ?: 1
            val response = remoteDataSource.getMovieReviews(movieId = movieId, page = page)
            val result = MovieDetailMappers.mapReviewResponseToDomain(response)

            LoadResult.Page(
                data = result.results,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.results.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}