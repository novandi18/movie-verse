package com.novandi.core.utils.mappers

import com.novandi.core.data.source.local.entity.MovieEntity
import com.novandi.core.data.source.local.entity.MovieTrendingEntity
import com.novandi.core.data.source.remote.response.MovieResponseItems
import com.novandi.core.domain.model.Movie
import com.novandi.core.utils.MovieType
import com.novandi.core.utils.getMovieGenre
import com.novandi.core.utils.toImageUrl
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

    fun mapPagingResponsesToDomain(input: List<MovieResponseItems>) : List<Movie> =
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

    fun mapTrendingResponsesToEntity(input: List<MovieResponseItems>, page: Int) : List<MovieTrendingEntity> =
        input.map {
            MovieTrendingEntity(
                id = it.id,
                title = it.title,
                poster = it.poster.toImageUrl(),
                releaseDate = it.releaseDate,
                overview = it.overview,
                voteAverage = it.voteAverage,
                genre = getMovieGenre(it.genres),
                page = page
            )
        }

    fun mapTrendingEntityToDomain(input: MovieTrendingEntity): Movie =
        Movie(
            id = input.id,
            title = input.title,
            poster = input.poster.toImageUrl(),
            releaseDate = input.releaseDate,
            overview = input.overview,
            voteAverage = input.voteAverage,
            genre = input.genre
        )
}