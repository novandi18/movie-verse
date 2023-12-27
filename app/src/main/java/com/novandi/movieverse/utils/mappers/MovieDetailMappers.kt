package com.novandi.movieverse.utils.mappers

import com.novandi.movieverse.data.source.remote.response.MovieDetailResponse
import com.novandi.movieverse.data.source.remote.response.MovieImagesResponse
import com.novandi.movieverse.data.source.remote.response.MovieReviewsResponse
import com.novandi.movieverse.domain.model.MovieDetail
import com.novandi.movieverse.domain.model.MovieDetailImages
import com.novandi.movieverse.domain.model.MovieReview
import com.novandi.movieverse.domain.model.MoviewReviewAuthor
import com.novandi.movieverse.domain.model.MoviewReviewItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object MovieDetailMappers {
    fun mapImagesResponsesToDomain(input: MovieImagesResponse) : Flow<List<MovieDetailImages>> {
        val images = ArrayList<MovieDetailImages>()
        input.posters!!.map {
            images.add(MovieDetailImages(filePath = it?.filePath.toString()))
        }
        return flowOf(images)
    }

    fun mapResponseToDomain(input: MovieDetailResponse) : Flow<MovieDetail> =
        flowOf(
            MovieDetail(
                id = input.id!!.toInt(),
                genre = input.genres!!.joinToString(separator = ", ") { it?.name.toString() },
                overview = input.overview.toString(),
                releaseDate = input.releaseDate.toString(),
                tagline = input.tagline.toString(),
                title = input.title.toString(),
                voteAverage = input.voteAverage.toString().toDouble(),
                voteCount = input.voteCount!!.toInt()
            )
        )

    fun mapReviewResponseToDomain(input: MovieReviewsResponse) : MovieReview {
        val reviewItem = input.results.map {
            val authorDetail = MoviewReviewAuthor(
                avatarPath = it.authorDetails.avatarPath,
                name = it.authorDetails.name,
                username = it.authorDetails.username
            )

            MoviewReviewItem(
                authorDetails = authorDetail,
                content = it.content,
                createdAt = it.createdAt,
            )
        }

        return MovieReview(
            id = input.id,
            page = input.page,
            results = reviewItem,
            totalResults = input.totalResults,
            totalPages = input.totalPages
        )
    }
}