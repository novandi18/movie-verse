package com.novandi.core.domain.model

data class User(
    val id: Int,
    val name: String,
    val username: String,
    val gravatar: String? = null,
    val tmdb: String? = null,
    var favoriteMovies: Int? = null,
    var ratedMovies: Int? = null,
    var watchlistMovies: Int? = null
)