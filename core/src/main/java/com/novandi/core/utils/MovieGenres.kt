package com.novandi.core.utils

data class DataGenre(
    val id: Int,
    val name: String
)

object MovieGenres {
    val genres = listOf(
        DataGenre(28, "Action"),
        DataGenre(12, "Adventure"),
        DataGenre(16, "Animation"),
        DataGenre(35, "Comedy"),
        DataGenre(80, "Crime"),
        DataGenre(99, "Documentary"),
        DataGenre(18, "Drama"),
        DataGenre(10751, "Family"),
        DataGenre(14, "Fantasy"),
        DataGenre(36, "History"),
        DataGenre(27, "Horror"),
        DataGenre(10402, "Music"),
        DataGenre(9648, "Mystery"),
        DataGenre(10749, "Romance"),
        DataGenre(878, "Science Fiction"),
        DataGenre(10770, "TV Movie"),
        DataGenre(53, "Thriller"),
        DataGenre(10752, "War"),
        DataGenre(37, "Western"),
    )
}