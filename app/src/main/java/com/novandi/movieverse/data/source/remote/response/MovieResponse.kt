package com.novandi.movieverse.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page")
    val page: String,

    @SerializedName("results")
    val results: List<MovieResponseItems>,

    @SerializedName("total_pages")
    val totalPages: String,

    @SerializedName("total_results")
    val totalResults: String,
)

data class MovieResponseItems(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("poster_path")
    val poster: String,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("genre_ids")
    val genres: List<Int>
)