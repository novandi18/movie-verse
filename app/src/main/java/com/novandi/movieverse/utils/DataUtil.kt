package com.novandi.movieverse.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.util.Locale

fun String?.toImageUrl() : String = "https://image.tmdb.org/t/p/w500$this"

fun getMovieGenre(genreIds: List<Int>): String {
    val genre = MovieGenres.genres.filter { it.id == genreIds[0] }
    return genre[0].name
}

fun String?.toImageUrlOriginal() : String = "https://image.tmdb.org/t/p/original$this"

fun String.formatDate(): String {
    return if (this != "") {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val date = inputFormat.parse(this)
        outputFormat.format(date!!)
    } else "Unknown"
}

fun Double.doubleToCeil(): String = String.format("%.1f", this)

fun String.splitAtIndex(index: Int) = when {
    index < 0 -> 0
    index > length -> length
    else -> index
}.let {
    take(it) to substring(it)
}

@SuppressLint("NewApi")
fun formatDateTime(dateStr: String): String {
    val dateTime = ZonedDateTime.parse(dateStr)
    val now = ZonedDateTime.now(ZoneOffset.UTC)
    val diff = Duration.between(dateTime, now)
    val diffYears = ChronoUnit.YEARS.between(dateTime, now)

    val minutes = diff.toMinutes()
    val hours = diff.toHours()
    val days = diff.toDays()
    val years = if (diffYears > 0) diffYears else 0
    val months = ChronoUnit.MONTHS.between(dateTime.plusYears(years), now)

    return when {
        years > 0 -> "$diffYears years ago"
        months > 0 -> "$months months ago"
        days > 0 -> "$days days ago"
        hours > 0 -> "$hours hours ago"
        minutes > 0 -> "$minutes minutes ago"
        else -> "Just now"
    }
}
