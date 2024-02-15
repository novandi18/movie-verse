package com.novandi.core.domain.model

data class RatedMovie(
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)
