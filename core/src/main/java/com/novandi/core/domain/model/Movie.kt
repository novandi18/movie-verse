package com.novandi.core.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val poster: String,
    val releaseDate: String,
    val overview: String,
    val voteAverage: Double,
    val genre: String,
    val movieType: String = ""
)