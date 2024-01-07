package com.novandi.core.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val genre: String,
    val overview: String,
    val releaseDate: String,
    val tagline: String,
    val voteAverage: Double,
    val voteCount: Int
)

data class MovieDetailImages(
    val filePath: String
)