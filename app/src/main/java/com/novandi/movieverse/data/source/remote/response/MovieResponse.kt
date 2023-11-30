package com.novandi.movieverse.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @get:SerializedName("page")
    val page: String,

    @get:SerializedName("results")
    val results: List<MovieResponseItems>
)

data class MovieResponseItems(
    @get:SerializedName("id")
    val id: Int,

    @get:SerializedName("title")
    val title: String,

    @get:SerializedName("poster_path")
    val poster: String,

    @get:SerializedName("release_date")
    val releaseDate: String,

    @get:SerializedName("overview")
    val overview: String,

    @get:SerializedName("vote_average")
    val voteAverage: String,
)