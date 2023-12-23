package com.novandi.movieverse.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun String?.toImageUrl() : String = "https://image.tmdb.org/t/p/w500$this"

fun getMovieGenre(genreIds: List<Int>): String {
    val genre = MovieGenres.genres.filter { it.id == genreIds[0] }
    return genre[0].name
}

fun String?.toImageUrlOriginal() : String = "https://image.tmdb.org/t/p/original$this"

fun String.formatDate(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val date = inputFormat.parse(this)
    return outputFormat.format(date!!)
}

fun Double.doubleToCeil(): String = String.format("%.1f", this)