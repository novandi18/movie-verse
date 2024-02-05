package com.novandi.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieSearchResponse(
    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val results: List<MovieSearchItems>,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_results")
    val totalResults: Int
)

data class MovieSearchItems(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String?,

    @SerializedName("poster_path")
    val poster: String?,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("overview")
    val overview: String?,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("genre_ids")
    val genres: List<Int>
)
