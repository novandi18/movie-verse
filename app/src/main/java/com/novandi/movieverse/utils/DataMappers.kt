package com.novandi.movieverse.utils

import com.novandi.movieverse.data.source.local.entity.MovieEntity
import com.novandi.movieverse.data.source.remote.response.MovieResponseItems
import com.novandi.movieverse.domain.model.Movie

object DataMappers {
    fun mapResponsesToEntities(input: List<MovieResponseItems>, movieType: String): List<MovieEntity> {
        val list = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                id = it.id,
                title = it.title,
                poster = it.poster.toImageUrl(),
                releaseDate = it.releaseDate,
                overview = it.overview,
                voteAverage = it.voteAverage,
                movieType = movieType
            )
            list.add(movie)
        }

        return list
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title = it.title,
                poster = it.poster,
                releaseDate = it.releaseDate,
                overview = it.overview,
                voteAverage = it.voteAverage,
                movieType = it.movieType
            )
        }

    fun mapDomainToEntity(input: Movie): MovieEntity = MovieEntity(
        id = input.id,
        title = input.title,
        poster = input.poster,
        releaseDate = input.releaseDate,
        overview = input.overview,
        voteAverage = input.voteAverage,
        movieType = input.movieType
    )
}