package com.novandi.movieverse.utils.mappers

import com.novandi.movieverse.data.source.remote.response.MovieDetailResponseAlt
import com.novandi.movieverse.data.source.remote.response.MovieImagesResponse
import com.novandi.movieverse.domain.model.MovieDetail
import com.novandi.movieverse.domain.model.MovieDetailImages
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

    fun mapResponseToDomain(input: MovieDetailResponseAlt) : Flow<MovieDetail> =
        flowOf(
            MovieDetail(
                id = input.id!!.toInt(),
                genre = "Action",
                overview = input.overview.toString(),
                releaseDate = input.releaseDate.toString(),
                tagline = input.tagline.toString(),
                title = input.title.toString(),
                voteAverage = input.voteAverage.toString().toDouble(),
                voteCount = input.voteCount!!.toInt()
            )
        )
}