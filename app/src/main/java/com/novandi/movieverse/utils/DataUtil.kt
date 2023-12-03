package com.novandi.movieverse.utils

fun String?.toImageUrl(): String = "https://image.tmdb.org/t/p/w500$this"

fun getMovieGenre(genreIds: List<Int>): String {
    val genre = MovieGenres.genres.filter { it.id == genreIds[0] }
    return genre[0].name
}