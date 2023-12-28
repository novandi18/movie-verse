package com.novandi.movieverse.utils.mappers

import com.novandi.movieverse.data.source.local.entity.MovieEntity
import com.novandi.movieverse.data.source.remote.response.MovieResponseItems
import com.novandi.movieverse.domain.model.Movie
import com.novandi.movieverse.utils.MovieType
import com.novandi.movieverse.utils.getMovieGenre
import com.novandi.movieverse.utils.toImageUrl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object MovieMappers {
    fun mapResponsesToEntities(input: List<MovieResponseItems>, movieType: MovieType) : List<MovieEntity> {
        val list = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                id = it.id,
                title = it.title,
                poster = it.poster.toImageUrl(),
                releaseDate = it.releaseDate,
                overview = it.overview,
                voteAverage = it.voteAverage,
                movieType = movieType.name,
                genre = getMovieGenre(it.genres)
            )
            list.add(movie)
        }

        return list
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>) : List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title = it.title,
                poster = it.poster,
                releaseDate = it.releaseDate,
                overview = it.overview,
                voteAverage = it.voteAverage,
                movieType = it.movieType,
                genre = it.genre
            )
        }

    fun mapResponsesToDomain(input: List<MovieResponseItems>) : Flow<List<Movie>> =
        flowOf(
            input.map {
                Movie(
                    id = it.id,
                    title = it.title,
                    poster = it.poster.toImageUrl(),
                    releaseDate = it.releaseDate,
                    overview = it.overview,
                    voteAverage = it.voteAverage,
                    genre = getMovieGenre(it.genres)
                )
            }
        )
}